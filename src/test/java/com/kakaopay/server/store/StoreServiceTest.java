package com.kakaopay.server.store;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class StoreServiceTest {

    @Autowired
    private StoreRepository storeRepository;


    @Test
    public void 상점등록(){
        //given
        Store store = new Store("abc-mart", StoreCategory.A);

        //when
        store = storeRepository.save(store);

        //then
        assertEquals(store.getId(), storeRepository.findById(store.getId()).get().getId());
    }

    @Test
    public void 상점명중복확인(){
        //given
        Store store = new Store("abcd-mart", StoreCategory.A);

        //when
        store = storeRepository.save(store);

        //then
        Store duplicateStore = new Store("abcd-mart", StoreCategory.B);
        Optional<Store> optionalStore = storeRepository.findByName(duplicateStore.getName());

        assertEquals(optionalStore.get().getName(), store.getName());
    }

}