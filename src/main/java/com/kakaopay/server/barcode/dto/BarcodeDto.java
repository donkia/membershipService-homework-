package com.kakaopay.server.barcode.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class BarcodeDto {
    @NotBlank(message = "값을 입력해주십시오.")
    @Size(min = 9, max = 9, message = "회원번호는 9자리 숫자로 입력해주십시오.")
    private String memberId;
}
