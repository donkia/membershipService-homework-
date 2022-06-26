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
        Optional<Store> store = storeRepository.findByName(storeCreateDto.getName());

        if(store.isPresent()){
            throw new ApiException(ApiExceptionEnum.FOUND_STORE);
        }
        else
            return storeRepository.save(new Store(storeCreateDto.getName(), storeCreateDto.getCategory()));
    }


}
