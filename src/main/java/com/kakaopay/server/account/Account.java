package com.kakaopay.server.account;

import com.kakaopay.server.BaseTimeEntity;
import com.kakaopay.server.store.StoreCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(indexes = @Index(name="i_barcode2", columnList = "barcode"))
/**
 * Account : 바코드별 상점 카테고리에 따른 남은 금액을 저장하는 Entity
 *
 * */
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    //키값

    private String barcode; //바코드 번호

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;    //상점 카테고리

    private Long price; //현재 금액

    public Account(){

    }

    public Account(String barcode, StoreCategory storeCategory, Long price){
        this.barcode = barcode;
        this.storeCategory = storeCategory;
        this.price = price;
    }
}
