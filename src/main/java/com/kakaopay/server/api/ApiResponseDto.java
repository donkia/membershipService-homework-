package com.kakaopay.server.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponseDto<T> {

    private String code;    //결과 코드

    private String message; // 상세 메시지

    private T body; //데이터

    @Builder
    public ApiResponseDto(String code, String message, T body){
        this.code = code;
        this.message = message;
        this.body = body;
    }
}
