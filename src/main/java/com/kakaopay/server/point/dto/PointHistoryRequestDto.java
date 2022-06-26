package com.kakaopay.server.point.dto;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Setter
public class PointHistoryRequestDto {

    private String barcode; //바코드

    @NotBlank(message = "조회 시작 일자를 입력해주세요.")
    @DateTimeFormat(pattern ="yyyyMMdd")
    private LocalDate startDate; //시작기간

    @NotBlank(message = "조회 종료 일자를 입력해주세요.")
    @DateTimeFormat(pattern ="yyyyMMdd")
    private LocalDate endDate; //종료기간

}
