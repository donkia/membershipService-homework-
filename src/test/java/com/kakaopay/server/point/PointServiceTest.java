package com.kakaopay.server.point;

import static org.junit.jupiter.api.Assertions.*;

import com.kakaopay.server.account.Account;
import com.kakaopay.server.account.AccountRepository;
import com.kakaopay.server.barcode.Barcode;
import com.kakaopay.server.barcode.BarcodeRepository;
import com.kakaopay.server.member.Member;
import com.kakaopay.server.member.MemberRepository;
import com.kakaopay.server.point.dto.PointSaveRequestDto;
import com.kakaopay.server.point.dto.PointSpendRequestDto;
import com.kakaopay.server.store.Store;
import com.kakaopay.server.store.StoreCategory;
import com.kakaopay.server.store.StoreRepository;
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



    @Test
    public void 포인트적립(){
        Store store = new Store("abc-mart", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);

        for(StoreCategory category : StoreCategory.values()){
            accountRepository.save(new Account(barcode.getId(), category, 0L));
        }

        Point point = new Point("earn", store.getCategory(), store.getName(), barcode.getId(), 500L);
        point = pointRepository.save(point);

        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(store.getCategory(), barcode.getId()).orElseThrow();
        account.setPrice(account.getPrice() + point.getPrice());
        //assertEquals(point.get);
        assertEquals(account.getPrice(), 500);
    }

    @Test
    @Transactional
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

        Long usePrice = 100L;
        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(store.getCategory(), barcode.getId()).orElse(new Account(barcode.getId(), store.getCategory(), 0L));

        if(account.getPrice() < usePrice){

        }
        else{
            account.setPrice(account.getPrice() - 100);
            Point point = new Point("use", store.getCategory(), store.getName(), barcode.getId(), 100L);
            point = pointRepository.save(point);

        }
        System.out.println(account);
    }

    @Test
    public void 포인트내역조회(){
        Store store = new Store("Astore", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);

        for(int i = 0; i < 5; i++){
            if( i % 2 == 1){
                Point point = new Point("use", store.getCategory(), store.getName(), barcode.getId(), 100L);
                point = pointRepository.save(point);
            }
            else{
                Point point = new Point("earn", store.getCategory(), store.getName(), barcode.getId(), 500L);
                point = pointRepository.save(point);
            }
        }

        List<Point> history = pointRepository.findPointByTermJPQL(LocalDate.now(), LocalDate.of(2022,06,26), barcode.getId());
        for(Point point : history){
            System.out.println(point);
        }
        assertEquals(history.size(), 5);
    }

    @Test
    @Transactional
    public void 포인트적립_동시접근() throws InterruptedException {

        Store store = new Store("Astore", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);

        Point point = new Point("earn", store.getCategory(), store.getName(), barcode.getId(), 500L);
        point = pointRepository.save(point);

        for(StoreCategory category : StoreCategory.values()){
            accountRepository.save(new Account(barcode.getId(), category, 0L));
        }

        AtomicInteger successCount = new AtomicInteger();
        int numberOfExcute = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);

        for (int i = 0; i < numberOfExcute; i++) {
            Barcode finalBarcode = barcode;
            Store finalStore = store;
            service.execute(() -> {
                try {
                    pointService.savePoint(new PointSaveRequestDto(1L, "1071637887", 100L));
                    successCount.getAndIncrement();
                    System.out.println("성공");
                } catch (ObjectOptimisticLockingFailureException oe) {
                    System.out.println("충돌감지");
                } catch (Exception e) {
                    System.out.println("exception : " + e.getMessage());
                }
                latch.countDown();
            });
        }
        latch.await();

        System.out.println("1 : " +  accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, barcode.getId()));
        //  Long price2 = pointRepository.findPriceByBarcodeIdJPQL(barcode.getId(), store.getCategory().toString());
        // System.out.println("price : " + price2);


    }
    
    @Test
    public void 포인트사용_동시접근() throws InterruptedException {

        Store store = new Store("Astore", StoreCategory.A);
        store = storeRepository.save(store);

        Member member = new Member();
        member = memberRepository.save(member);

        Barcode barcode = new Barcode(member);
        barcode = barcodeRepository.save(barcode);


        for(StoreCategory category : StoreCategory.values()){
            accountRepository.save(new Account(barcode.getId(), category, 1000L));
        }

        AtomicInteger successCount = new AtomicInteger();
        int numberOfExcute = 10;
        ExecutorService service = Executors.newFixedThreadPool(10);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);

        for (int i = 0; i < numberOfExcute; i++) {
            Barcode finalBarcode = barcode;
            Store finalStore = store;
            service.execute(() -> {
                try {
                    pointService.spendPoint(new PointSpendRequestDto(1L, finalBarcode.getId() ,200L));
                    successCount.getAndIncrement();

                    Point point = new Point("use", finalStore.getCategory(), finalStore.getName(), finalBarcode.getId(), 500L);
                    point = pointRepository.save(point);
                    System.out.println("성공");
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