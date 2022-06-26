package com.kakaopay.server.api;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    private ApiExceptionEnum apiExceptionEnum;

    public ApiException(ApiExceptionEnum e){
        super(e.getMessage());
        this.apiExceptionEnum = e;
    }
}
