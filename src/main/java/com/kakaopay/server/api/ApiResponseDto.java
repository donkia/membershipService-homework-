package com.kakaopay.server.api;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ApiResponseDto<T> {

    private String code;

    private String message;

    private T body;

    @Builder
    public ApiResponseDto(String code, String message, T body){
        this.code = code;
        this.message = message;
        this.body = body;
    }
}
