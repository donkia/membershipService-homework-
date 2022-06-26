package com.kakaopay.server.barcode;

import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.barcode.dto.BarcodeDto;
import com.kakaopay.server.api.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BarcodeController {

    private final BarcodeService barcodeService;

    @PostMapping("/barcode")
    public ResponseEntity<?> createBarcode(@Valid BarcodeDto barcodeDto, BindingResult bindingResult){
        log.info("BarcodeDto : " + barcodeDto);

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    ApiResponseDto.builder()
                            .code(ApiExceptionEnum.INVALID_PARAMETER.getCode())
                            .message(ApiExceptionEnum.INVALID_PARAMETER.getMessage() + bindingResult.getFieldError().getDefaultMessage())
                            .body(null).build(), HttpStatus.BAD_REQUEST);
        }
        Barcode barcode = barcodeService.createBarcode(barcodeDto.getMemberId());
        return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", barcode), HttpStatus.OK);
    }
}
