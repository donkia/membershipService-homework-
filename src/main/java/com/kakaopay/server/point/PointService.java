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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PointService {

    private final PointRepository pointRepository;
    private final StoreRepository storeRepository;
    private final BarcodeRepository barcodeRepository;
    private final AccountRepository accountRepository;


    public Point savePoint(PointSaveRequestDto pointSaveRequestDto){

        Store store = storeRepository.findById(pointSaveRequestDto.getStoreId()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_STORE));
        Barcode barcode = barcodeRepository.findById(pointSaveRequestDto.getBarcode()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_BARCODE));

        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, pointSaveRequestDto.getBarcode()).orElse(new Account(pointSaveRequestDto.getBarcode(), store.getCategory(), 0L));
        account.setPrice(account.getPrice() + pointSaveRequestDto.getPrice());
        System.out.println("account price : " + account.getPrice());

        return pointRepository.save(Point.builder().price(pointSaveRequestDto.getPrice()).category(store.getCategory()).storeName(store.getName()).type("earn").barcodeId(barcode.getId()).build());


    }

    public Point spendPoint(PointSpendRequestDto pointSpendRequestDto){

        Store store = storeRepository.findById(pointSpendRequestDto.getStoreId()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_STORE));
        Barcode barcode = barcodeRepository.findById(pointSpendRequestDto.getBarcode()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_BARCODE));

        // 상점 카테고리에 따른 남은 포인트 금액 확인
        Long spendPoint = pointSpendRequestDto.getPrice();
        Account account = accountRepository.findAccountByStoreCategoryAndBarcode(StoreCategory.A, pointSpendRequestDto.getBarcode()).orElse(new Account(pointSpendRequestDto.getBarcode(), store.getCategory(), 0L));

        System.out.println("savedPoint : " + account.getPrice() +", spendPoint : " + spendPoint);
        if(account.getPrice() <= 0 || account.getPrice() - spendPoint < 0){
            throw new ApiException(ApiExceptionEnum.NOT_FOUND_POINT);
        }
        else{
            account.setPrice(account.getPrice() - spendPoint);
            Point newPoint = Point.builder().price(pointSpendRequestDto.getPrice()).category(store.getCategory()).storeName(store.getName()).type("use").barcodeId(barcode.getId()).build();
            return pointRepository.save(newPoint);
        }
    }


    public List<Point> historyPoint(PointHistoryRequestDto pointHistoryRequestDto){
        Barcode barcode = barcodeRepository.findById(pointHistoryRequestDto.getBarcode()).orElseThrow(() -> new ApiException(ApiExceptionEnum.NOT_FOUND_BARCODE));

        return pointRepository.findPointByTermJPQL(pointHistoryRequestDto.getStartDate(), pointHistoryRequestDto.getEndDate(), barcode.getId());

    }

}
