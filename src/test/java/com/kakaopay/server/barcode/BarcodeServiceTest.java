package com.kakaopay.server.barcode;

import com.kakaopay.server.account.Account;
import com.kakaopay.server.account.AccountRepository;
import com.kakaopay.server.member.Member;
import com.kakaopay.server.member.MemberRepository;
import com.kakaopay.server.store.StoreCategory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
@SpringBootTest
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
            Member member = memberRepository.save(new Member());
            Barcode barcode = new Barcode(member);
            barcode = barcodeRepository.save(barcode);

            for(StoreCategory storeCategory : StoreCategory.values()) {
                accountRepository.save(new Account(barcode.getId(), storeCategory, 0L));
            }
            assertEquals(accountRepository.findAccountByBarcode(barcode.getId()).size(), 3);
            System.out.println(barcode);
        }
    }

    @Test
    @DisplayName("회원이 아닐때 멤버십 바코드 등록")
    public void 비회원바코드등록(){
        Optional<Member> member = memberRepository.findById("111");
        assertEquals(member, Optional.empty());
    }


}