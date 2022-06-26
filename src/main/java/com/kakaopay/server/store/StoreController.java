package com.kakaopay.server.store;

import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.api.ApiResponseDto;
import com.kakaopay.server.store.dto.StoreCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StoreController {

    private final StoreService storeService;

    //상점 등록
    @PostMapping("/store")
    public ResponseEntity<?> create(@Valid StoreCreateDto storeCreateDto, BindingResult bindingResult){

        // 입력 Dto 데이터 검증
        if(storeCreateDto.getCategory() == null){
            return new ResponseEntity<>(
                    ApiResponseDto.builder()
                            .code(ApiExceptionEnum.NOT_FOUND_CATEGORY.getCode())
                            .message(ApiExceptionEnum.NOT_FOUND_CATEGORY.getMessage())
                            .body(null).build(),ApiExceptionEnum.NOT_FOUND_CATEGORY.getHttpStatus());
        }

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    ApiResponseDto.builder()
                            .code(ApiExceptionEnum.INVALID_PARAMETER.getCode())
                            .message(ApiExceptionEnum.INVALID_PARAMETER.getMessage() + bindingResult.getFieldError().getDefaultMessage())
                            .body(null).build(), HttpStatus.BAD_REQUEST);
        }

        // 입력값 Dto 검증 후 상점(store) 정보 생성
        Store store = storeService.create(storeCreateDto);
        return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", store),HttpStatus.OK);
    }
}
