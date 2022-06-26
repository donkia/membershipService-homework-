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
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;

    @Enumerated(EnumType.STRING)
    private StoreCategory storeCategory;

    private Long price;

    public Account(){

    }

    public Account(String barcode, StoreCategory storeCategory, Long price){
        this.barcode = barcode;
        this.storeCategory = storeCategory;
        this.price = price;
    }
}
