package com.kakaopay.server.store.dto;

import com.kakaopay.server.store.StoreCategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class StoreCreateDto {

    @NotBlank(message = "상점 이름을 입력해주십시오.")
    private String name;

    private StoreCategory category;
}
