package com.kakaopay.server.barcode;

import com.kakaopay.server.BaseTimeEntity;
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
public class Barcode extends BaseTimeEntity {

    @Id
    @GenericGenerator(name="barcode_id", strategy = "com.kakaopay.server.barcode.BarcodeIdGenerator")
    @GeneratedValue(generator = "barcode_id")
    @Column(name="barcode_id")
    private String id;      //바코드 번호

    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;  // 멤버id

    public Barcode(Member member){
        this.member = member;
    }

    public Barcode(){

    }
}
