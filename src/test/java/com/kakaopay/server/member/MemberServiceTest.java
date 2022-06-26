package com.kakaopay.server.member;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;


    @Test
    public void 회원등록(){
        //given
        Member member = new Member();

        //when
        member = memberRepository.save(member);

        //then
        assertEquals("000000002", member.getId());
    }
}