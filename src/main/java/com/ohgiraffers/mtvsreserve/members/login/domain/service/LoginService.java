package com.ohgiraffers.mtvsreserve.members.login.domain.service;

import com.ohgiraffers.mtvsreserve.members.login.domain.entity.MemberEntity;
import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import com.ohgiraffers.mtvsreserve.members.login.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO.toMemberDTO;


@Service
@RequiredArgsConstructor
public class LoginService {

    private final MemberRepository memberRepository;

    /**
     * @return null 로그인 실패
     */

    public MemberDTO login(String loginId, String password) {
        /*
        1. 회원이 입력한 이메일로 DB에서 조회를 함
        2. DB에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
         */

        Optional<MemberEntity> byMemberLoginId =  memberRepository.findByLoginId(loginId) ;
        if(byMemberLoginId.isPresent()){
            //조회 결과가 있다.
            MemberEntity memberEntity = byMemberLoginId.get();
            if(memberEntity.getPassword().equals(password)) {
                //비밀번호 일치
                //entity->dto변환후 리턴
                MemberDTO dto = toMemberDTO(memberEntity);
                return dto;
            }else{
                //비밀번호 로그인 실패
                return null;
            }
        }else{
            //조회 결과가 없다(해당 아이디 가진 회원이 없다)
            return null;
        }
    }

}
