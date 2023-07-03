package com.ohgiraffers.mtvsreserve.members.login.domain.entity;

import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "member_table")
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String loginId;

    @Column
    private String name;

    @Column
    private String major;

    @Column
    private String password;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setName(memberDTO.getName());
        memberEntity.setMajor(memberDTO.getMajor());
        memberEntity.setLoginId(memberDTO.getLoginId());
        memberEntity.setPassword(memberDTO.getPassword());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setId(memberDTO.getId());
        memberEntity.setName(memberDTO.getName());
        memberEntity.setMajor(memberDTO.getMajor());
        memberEntity.setLoginId(memberDTO.getLoginId());
        memberEntity.setPassword(memberDTO.getPassword());
        return memberEntity;
    }
}
