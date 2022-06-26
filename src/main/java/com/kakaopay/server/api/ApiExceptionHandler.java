package com.kakaopay.server.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> apiException(ApiException e){
        return new ResponseEntity<>(
                ApiResponseDto.builder()
                        .code(e.getApiExceptionEnum().getCode())
                        .message(e.getApiExceptionEnum().getMessage())
                        .body(null).build(), e.getApiExceptionEnum().getHttpStatus());
    }


    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<?> ArgumentException(IllegalArgumentException e){
        return new ResponseEntity<>(new ApiResponseDto<>("InvalidParameter", e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> exception(Exception e){
        log.error("시스템 에러", e);
        return new ResponseEntity<>(
                ApiResponseDto.builder()
                        .code(ApiExceptionEnum.INTERNAL_SERVER_ERROR.getCode())
                        .message(ApiExceptionEnum.INTERNAL_SERVER_ERROR.getMessage())
                        .body(null).build(), ApiExceptionEnum.INTERNAL_SERVER_ERROR.getHttpStatus());

    }
}
