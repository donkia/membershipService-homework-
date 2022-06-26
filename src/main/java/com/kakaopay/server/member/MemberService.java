package com.kakaopay.server.member;

import com.kakaopay.server.account.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;


    public Member createMember(){
        return memberRepository.save(new Member());
    }
}
