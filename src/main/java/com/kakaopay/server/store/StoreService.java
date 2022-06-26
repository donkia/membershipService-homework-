package com.kakaopay.server.store;

import com.kakaopay.server.api.ApiException;
import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.store.dto.StoreCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;


    public Store create(StoreCreateDto storeCreateDto) {
        // 기존에 등록된 상점명이 있는지 확인
        Optional<Store> store = storeRepository.findByName(storeCreateDto.getName());

        // 등록된 상점명이 있을 경우 에러 리턴
        if(store.isPresent()){
            throw new ApiException(ApiExceptionEnum.FOUND_STORE);
        }
        // 등록된 상점명이 없으면 신규 데이터 생성
        else
            return storeRepository.save(new Store(storeCreateDto.getName(), storeCreateDto.getCategory()));
    }


}
