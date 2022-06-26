package com.kakaopay.server.api;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiExceptionEnum {

    NOT_FOUND_STORE("ERROR-S001", "등록되지 않은 상점입니다.", HttpStatus.BAD_REQUEST),
    FOUND_STORE("ERROR-S002", "이미 등록된 상점명입니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_CATEGORY("ERROR-S003", "등록된 상점 카테고리가 아닙니다.(A,B,C)", HttpStatus.BAD_REQUEST),

    NOT_FOUND_MEMBER("ERROR-M001", "등록되지 않은 회원입니다.", HttpStatus.BAD_REQUEST),

    NOT_FOUND_BARCODE("ERROR-B001", "등록되지 않은 바코드 번호입니다.", HttpStatus.BAD_REQUEST),

    NOT_FOUND_POINT("ERROR-P001", "포인트가 부족하여 사용할 수 없습니다.", HttpStatus.BAD_REQUEST),

    INTERNAL_SERVER_ERROR("ERROR-E001", "API 처리 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PARAMETER("ERROR-E002", "입력값 오류 : ", HttpStatus.BAD_REQUEST),
    ;

    private String code;
    private String message;
    private HttpStatus httpStatus;

    ApiExceptionEnum(String code, String message, HttpStatus httpStatus){
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
