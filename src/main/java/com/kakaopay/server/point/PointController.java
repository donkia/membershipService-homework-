package com.kakaopay.server.point;

import com.kakaopay.server.api.ApiExceptionEnum;
import com.kakaopay.server.api.ApiResponseDto;
import com.kakaopay.server.point.dto.PointHistoryRequestDto;
import com.kakaopay.server.point.dto.PointHistoryResponseDto;
import com.kakaopay.server.point.dto.PointSaveRequestDto;
import com.kakaopay.server.point.dto.PointSpendRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    //포인트 적립
    @PostMapping("/point/save")
    public ResponseEntity<?> savePoint(@Valid PointSaveRequestDto pointSaveRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    ApiResponseDto.builder()
                            .code(ApiExceptionEnum.INVALID_PARAMETER.getCode())
                            .message(ApiExceptionEnum.INVALID_PARAMETER.getMessage() + bindingResult.getFieldError().getDefaultMessage())
                            .body(null).build(), HttpStatus.BAD_REQUEST);
        }
        System.out.println("1");
        Point point = pointService.savePoint(pointSaveRequestDto);
        System.out.println("2");
        return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", point), HttpStatus.OK);
    }

    // 포인트 사용
    @PostMapping("/point/spend")
    public ResponseEntity<?> spendPoint(@Valid PointSpendRequestDto pointSpendRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    ApiResponseDto.builder()
                            .code(ApiExceptionEnum.INVALID_PARAMETER.getCode())
                            .message(ApiExceptionEnum.INVALID_PARAMETER.getMessage() + bindingResult.getFieldError().getDefaultMessage())
                            .body(null).build(), HttpStatus.BAD_REQUEST);
        }

        Point point = pointService.spendPoint(pointSpendRequestDto);
        return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", point), HttpStatus.OK);
    }

    // 포인트 내역 조회
    @GetMapping("/point/history")
    public ResponseEntity<?> getHistoryByTerm(@Valid PointHistoryRequestDto pointHistoryRequestDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(
                    ApiResponseDto.builder()
                            .code(ApiExceptionEnum.INVALID_PARAMETER.getCode())
                            .message(ApiExceptionEnum.INVALID_PARAMETER.getMessage() + bindingResult.getFieldError().getDefaultMessage())
                            .body(null).build(), HttpStatus.BAD_REQUEST);
        }

        List<Point> history = pointService.historyPoint(pointHistoryRequestDto);
        List<PointHistoryResponseDto> dto = new ArrayList<>();
        for(Point p : history){
            dto.add(p.dto());
        }
        return new ResponseEntity<>(new ApiResponseDto<>("Success", "성공", dto), HttpStatus.OK);
    }
}
