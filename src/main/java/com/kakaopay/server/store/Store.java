package com.kakaopay.server.store;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private StoreCategory category;

    @Builder
    public Store(String name, StoreCategory category){
        this.name = name;
        this.category = category;
    }

    public Store(){

    }
}
