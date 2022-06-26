package com.kakaopay.server.member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void 회원등록(){

        Member member = new Member();

        memberRepository.save(member);

        List<Member> memberList = memberRepository.findAll();
        System.out.println(memberList.get(0));
    }

}