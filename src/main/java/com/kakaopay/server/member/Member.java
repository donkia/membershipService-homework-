package com.kakaopay.server.member;

import com.kakaopay.server.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Member extends BaseTimeEntity {

    @Id
    @GenericGenerator(name="member_id", strategy = "com.kakaopay.server.member.MemberIdGenerator")
    @GeneratedValue(generator = "member_id")
    @Column(name="member_id")
    private String id;



    public Member() {

    }
}
