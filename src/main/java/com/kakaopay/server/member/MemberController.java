package com.kakaopay.server.member;

import com.kakaopay.server.api.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원 가입
    @PostMapping("/member")
    public ResponseEntity<?> createMember(){
        Member member = memberService.createMember();
        return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", member), HttpStatus.OK);
    }

}
