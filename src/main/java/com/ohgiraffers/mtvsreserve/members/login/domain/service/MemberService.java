package com.ohgiraffers.mtvsreserve.members.login.domain.service;

import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import com.ohgiraffers.mtvsreserve.members.login.domain.entity.MemberEntity;
import com.ohgiraffers.mtvsreserve.members.login.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ohgiraffers.mtvsreserve.members.login.domain.entity.MemberEntity.toMemberEntity;
import static com.ohgiraffers.mtvsreserve.members.login.domain.entity.MemberEntity.toUpdateMemberEntity;
import static com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO.toMemberDTO;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberDTO save(MemberDTO memberDTO) {
        MemberEntity memberEntity = toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
        return memberDTO;
    }

    public MemberDTO duplicationCheckById(String loginId) {
        /*
        1. 회원이 입력한 아이디로 DB에서 조회를 함
        2. DB에서 조회한 아이디와 사용자가 입력한 아이디가 중복되는지 판단
         */

        Optional<MemberEntity> duplicationIdMember =  memberRepository.findByLoginId(loginId) ;
        if(duplicationIdMember.isPresent()){
            //조회 결과가 있다. 아이디가 중복되어있다.
            MemberEntity memberEntity = duplicationIdMember.get();
            MemberDTO dto = toMemberDTO(memberEntity);
            return dto;
        }else{
            //조회 결과가 없다(해당 아이디 가진 회원이 없다)
            return null;
        }
    }

    public List<MemberDTO> findAll(){
        List<MemberEntity> memberEntityList=memberRepository.findAll();
        List<MemberDTO> memberDTOList=new ArrayList<>();
        for (MemberEntity memberEntity:memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findByLoginId(String LoginId){
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByLoginId(LoginId);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public MemberDTO updateForm(String LoginId){
        Optional<MemberEntity> optionalMemberEntity=memberRepository.findByLoginId(LoginId);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }
}
