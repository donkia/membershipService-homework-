package com.kakaopay.server.point;

import com.kakaopay.server.BaseTimeEntity;
import com.kakaopay.server.point.dto.PointHistoryResponseDto;
import com.kakaopay.server.store.StoreCategory;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(indexes = @Index(name="i_barcode", columnList = "barcode"))
public class Point extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate approvedAt;

    private String type;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    private String storeName;

    private String barcode;

    private Long price;

    @Builder
    public Point(String type, StoreCategory category, String storeName, String barcode, Long price){
        this.approvedAt = LocalDate.now();
        this.type = type;
        this.category = category;
        this.storeName = storeName;
        this.barcode = barcode;
        this.price = price;
    }

    public Point() {

    }

    public PointHistoryResponseDto dto(){
        return PointHistoryResponseDto.builder().approvedAt(this.getCreatedDate())
                .barcode(this.barcode)
                .category(this.category)
                .price(this.price)
                .storeName(this.storeName)
                .type(this.type)
                .build();
    }
}
