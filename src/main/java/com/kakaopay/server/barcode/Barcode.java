package com.kakaopay.server.barcode;

import com.kakaopay.server.member.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
public class Barcode {

    @Id
    @GenericGenerator(name="barcode_id", strategy = "com.kakaopay.server.barcode.BarcodeIdGenerator")
    @GeneratedValue(generator = "barcode_id")
    @Column(name="barcode_id")
    private String id;

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public Barcode(Member member){
        this.member = member;
    }

    public Barcode(){

    }
}
