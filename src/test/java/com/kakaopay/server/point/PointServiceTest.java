package com.kakaopay.server.point;

import static org.junit.jupiter.api.Assertions.*;

import com.kakaopay.server.account.Account;
import com.kakaopay.server.account.AccountRepository;
import com.kakaopay.server.api.ApiException;
import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.barcode.Barcode;
import com.kakaopay.server.barcode.BarcodeRepository;
import com.kakaopay.server.member.Member;
import com.kakaopay.server.member.MemberRepository;
import com.kakaopay.server.point.dto.PointSaveRequestDto;
import com.kakaopay.server.point.dto.PointSpendRequestDto;
import com.kakaopay.server.store.Store;
import com.kakaopay.server.store.StoreCategory;
import com.kakaopay.server.store.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PointServiceTest {

    @Autowired
    private PointRepository pointRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private PointService pointService;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    public void initData(){

    }

    @Test
    public void 포인트적립(){

        //given
        Store store = new Store("abc-mart", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);

        for(StoreCategory category : StoreCategory.values()){
            accountRepository.save(new Account(barcode.getId(), category, 0L));
        }

        //when
        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(store.getCategory(), barcode.getId()).orElse(new Account(barcode.getId(), store.getCategory(), 0L));
        account.setPrice(account.getPrice() + 500);

        //then
        Point point = new Point("earn", store.getCategory(), store.getName(), barcode.getId(), 500L);
        point = pointRepository.save(point);

        assertEquals(account.getPrice(), 500);
        assertEquals(point.getId(), pointRepository.findById(point.getId()).get().getId());
    }

    @Test
    public void 포인트사용(){

        //given
        Store store = new Store("abc-mart", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);

        for(StoreCategory category : StoreCategory.values()){
            accountRepository.save(new Account(barcode.getId(), category, 500L));
        }

        //when
        Long spendPrice = 100L;
        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(store.getCategory(), barcode.getId()).orElse(new Account(barcode.getId(), store.getCategory(), 0L));

        //then
        if(account.getPrice() <= 0 ||account.getPrice() - spendPrice < 0){
            throw new ApiException(ApiExceptionEnum.NOT_FOUND_POINT);
        }
        else{
            account.setPrice(account.getPrice() - 100);
            Point point = new Point("use", store.getCategory(), store.getName(), barcode.getId(), 100L);
            point = pointRepository.save(point);

            assertEquals(400, account.getPrice());
            assertEquals(point.getId(), pointRepository.findById(point.getId()).get().getId());
        }
    }

    @Test
    public void 포인트내역조회(){
        //given
        Store store = new Store("Astore", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);

        //when
        for(int i = 0; i < 5; i++){
            if( i % 2 == 1){
                Point usePoint = new Point("use", store.getCategory(), store.getName(), barcode.getId(), 100L);
                usePoint = pointRepository.save(usePoint);
            }
            else{
                Point earnPoint = new Point("earn", store.getCategory(), store.getName(), barcode.getId(), 500L);
                earnPoint = pointRepository.save(earnPoint);
            }
        }

        //then
        List<Point> history = pointRepository.findPointByTermJPQL(LocalDate.now(), LocalDate.now(), barcode.getId());
        assertEquals(5, history.size());
    }

    @Test
    public void 포인트적립_동시접근() throws InterruptedException {

        // given
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExcute = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);


        //when
        for (int i = 0; i < numberOfExcute; i++) {
            int finalI = i;
            service.execute(() -> {
                try {
                    pointService.savePoint(new PointSaveRequestDto(1L, "1071637887", 100L));
                    successCount.getAndIncrement();

                    //then
                    assertEquals(accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, "1071637887").get().getPrice(), 100*(finalI +1));
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println("exception : " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();
    }
    
    @Test
    public void 포인트사용_동시접근() throws InterruptedException {

        //given
        AtomicInteger successCount = new AtomicInteger();
        int numberOfExcute = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);

        //when
        for (int i = 0; i < numberOfExcute; i++) {
            int finalI = i;

            service.execute(() -> {
                try {
                    pointService.spendPoint(new PointSpendRequestDto(1L, "1071637887" ,200L));
                    successCount.getAndIncrement();


                    //then
                    assertEquals(accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, "1071637887").get().getPrice(), 2000-200*(finalI +1));

                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println("exception : " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();
    }

}