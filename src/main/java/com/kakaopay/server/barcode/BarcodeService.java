package com.kakaopay.server.barcode;

import com.kakaopay.server.account.Account;
import com.kakaopay.server.account.AccountRepository;
import com.kakaopay.server.api.ApiException;
import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.member.Member;
import com.kakaopay.server.member.MemberRepository;
import com.kakaopay.server.store.StoreCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BarcodeService {

    private final BarcodeRepository barcodeRepository;
    private final MemberRepository memberRepository;
    private final AccountRepository accountRepository;


    public Barcode createBarcode(String memberId){
        Optional<Member> member = memberRepository.findById(memberId);


        if(member.isEmpty()){
            throw new ApiException(ApiExceptionEnum.NOT_FOUND_MEMBER);
        }

        Optional<Barcode> barcode = barcodeRepository.findByMemberId(memberId);
        if(barcode.isPresent()){
            return barcode.get();
        }
        else{
            Barcode newBarcode = barcodeRepository.save(new Barcode(member.get()));
            for(StoreCategory category : StoreCategory.values()){
                accountRepository.save(new Account(newBarcode.getId(), category, 0L));
            }

            return newBarcode;
        }

    }
}
