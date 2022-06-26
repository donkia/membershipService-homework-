package com.kakaopay.server.store;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StoreServiceTest {

    @Autowired
    private StoreRepository storeRepository;


    @Test
    public void 상점등록(){

        Store store = new Store("abc-mart", StoreCategory.A);
        store = storeRepository.save(store);


    }

}