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

    //바코드 발급
    public Barcode createBarcode(String memberId){
        //가입된 멤버인지 확인
        Optional<Member> member = memberRepository.findById(memberId);

        if(member.isEmpty()){
            throw new ApiException(ApiExceptionEnum.NOT_FOUND_MEMBER);
        }

        //이미 발급된 바코드인지 확인
        Optional<Barcode> barcode = barcodeRepository.findByMemberId(memberId);
        if(barcode.isPresent()){
            //이미 발급되었으면 부여된 바코드 리턴
            return barcode.get();
        }
        else{
            //신규 발급일 경우
            Barcode newBarcode = barcodeRepository.save(new Barcode(member.get()));
            //상점 카테고리(A,B,C)에 따라 바코드별 계좌(account) 생성
            for(StoreCategory category : StoreCategory.values()){
                accountRepository.save(new Account(newBarcode.getId(), category, 0L));
            }

            return newBarcode;
        }

    }
}
