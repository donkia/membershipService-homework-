package com.kakaopay.server.point;

import com.kakaopay.server.account.Account;
import com.kakaopay.server.account.AccountRepository;
import com.kakaopay.server.api.ApiException;
import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.barcode.Barcode;
import com.kakaopay.server.barcode.BarcodeRepository;
import com.kakaopay.server.point.dto.PointHistoryRequestDto;
import com.kakaopay.server.point.dto.PointSaveRequestDto;
import com.kakaopay.server.point.dto.PointSpendRequestDto;
import com.kakaopay.server.store.Store;
import com.kakaopay.server.store.StoreCategory;
import com.kakaopay.server.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class PointService {

    private final PointRepository pointRepository;
    private final StoreRepository storeRepository;
    private final BarcodeRepository barcodeRepository;
    private final AccountRepository accountRepository;


    public Point savePoint(PointSaveRequestDto pointSaveRequestDto){

        //등록된 상점인지 확인
        Store store = storeRepository.findById(pointSaveRequestDto.getStoreId()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_STORE));
        
        //등록된 바코드인지 확인
        Barcode barcode = barcodeRepository.findById(pointSaveRequestDto.getBarcode()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_BARCODE));

        //상점카테고리에 따른 바코드 계좌에 금액 확인 후 적립
        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, pointSaveRequestDto.getBarcode()).orElse(new Account(pointSaveRequestDto.getBarcode(), store.getCategory(), 0L));
        account.setPrice(account.getPrice() + pointSaveRequestDto.getPrice());
        
        //포인트 내역 저장
        return pointRepository.save(Point.builder().price(pointSaveRequestDto.getPrice()).category(store.getCategory()).storeName(store.getName()).type("earn").barcode(barcode.getId()).build());
    }

    public Point spendPoint(PointSpendRequestDto pointSpendRequestDto){

        //등록된 상점인지 확인
        Store store = storeRepository.findById(pointSpendRequestDto.getStoreId()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_STORE));

        //등록된 바코드인지 확인
        Barcode barcode = barcodeRepository.findById(pointSpendRequestDto.getBarcode()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_BARCODE));

        // 상점 카테고리에 따른 남은 바코드 계좌 금액 확인
        Long spendPoint = pointSpendRequestDto.getPrice();
        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, pointSpendRequestDto.getBarcode()).orElse(new Account(pointSpendRequestDto.getBarcode(), store.getCategory(), 0L));

        log.info("savedPoint : " + account.getPrice() +", spendPoint : " + spendPoint);
        // 금액이 부족할 경우 에러 리턴
        if(account.getPrice() <= 0 || account.getPrice() - spendPoint < 0){
            throw new ApiException(ApiExceptionEnum.NOT_FOUND_POINT);
        }
        // 금액이 부족하지 않으면 포인트 사용
        else{
            account.setPrice(account.getPrice() - spendPoint);
            Point newPoint = Point.builder().price(pointSpendRequestDto.getPrice()).category(store.getCategory()).storeName(store.getName()).type("use").barcode(barcode.getId()).build();
            
            //포인트 사용 내역 저장
            return pointRepository.save(newPoint);
        }
    }


    public List<Point> historyPoint(PointHistoryRequestDto pointHistoryRequestDto){
        //등록된 바코드인지 확인
        Barcode barcode = barcodeRepository.findById(pointHistoryRequestDto.getBarcode()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_BARCODE));

        //포인트 사용 내역 확인
        return pointRepository.findPointByTermJPQL(pointHistoryRequestDto.getStartDate(), pointHistoryRequestDto.getEndDate(), barcode.getId());

    }

}
