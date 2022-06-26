package com.kakaopay.server.point.dto;

import com.kakaopay.server.store.StoreCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.tomcat.jni.Local;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PointHistoryResponseDto {

    private LocalDateTime approvedAt;

    private String type;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private String storeName;

    private String barcodeId;

    private Long price;

    @Builder
    public PointHistoryResponseDto(LocalDateTime approvedAt, String type, StoreCategory category, String storeName, String barcodeId, Long price){
        this.approvedAt = approvedAt;
        this.type = type;
        this.category = category;
        this.storeName = storeName;
        this.barcodeId = barcodeId;
        this.price = price;
    }
}
