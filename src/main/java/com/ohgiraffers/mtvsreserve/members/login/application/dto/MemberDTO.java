package com.ohgiraffers.mtvsreserve.members.login.application.dto;

import com.ohgiraffers.mtvsreserve.members.login.domain.entity.MemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private Long id;

    @NotEmpty
    private String name;
    @NotEmpty
    private String major;
    @NotEmpty
    private String loginId;
    @NotEmpty
    private String password;

    public static MemberDTO toMemberDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setName(memberEntity.getName());
        memberDTO.setMajor(memberEntity.getMajor());
        memberDTO.setLoginId(memberEntity.getLoginId());
        memberDTO.setPassword(memberEntity.getPassword());
        return memberDTO;
    }
}
