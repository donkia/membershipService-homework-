package com.kakaopay.server.barcode;

import com.kakaopay.server.account.Account;
import com.kakaopay.server.account.AccountRepository;
import com.kakaopay.server.member.Member;
import com.kakaopay.server.member.MemberRepository;
import com.kakaopay.server.store.StoreCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class BarcodeServiceTest {

    @Autowired
    private BarcodeRepository barcodeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private AccountRepository accountRepository;
    
    @Test
    @DisplayName("멤버십 바코드 등록")
    public void 멤버쉽바코드발급(){

        for(int i = 0; i < 10; i++) {
            //given
            Member member = memberRepository.save(new Member());
            Barcode barcode = new Barcode(member);

            //when
            barcode = barcodeRepository.save(barcode);
            
            //바코드가 새로 만들어지면 상점 카테고리별 계좌 생성
            for(StoreCategory storeCategory : StoreCategory.values()) {
                accountRepository.save(new Account(barcode.getId(), storeCategory, 0L));
            }

            //then
            assertEquals(accountRepository.findAccountByBarcode(barcode.getId()).size(), 3);
        }
    }

    @Test
    @DisplayName("회원이 아닐때 멤버십 바코드 등록")
    public void 비회원바코드등록(){
        //given, when
        Optional<Member> member = memberRepository.findById("111");

        //then
        assertEquals(member, Optional.empty());
    }


}