package com.kakaopay.server.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter
@Setter
@AllArgsConstructor
public class PointSpendRequestDto {

    @NotNull(message = "상점id를 입력해주세요.")
    private Long storeId;   //상점id

    @NotBlank(message = "멤버십 바코드를 입력해주세요.")
    private String barcode; //바코드

    @NotNull(message = "적립 금액을 입력해주세요.")
    @PositiveOrZero
    private Long price; //사용금액

}
