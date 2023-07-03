package com.ohgiraffers.mtvsreserve.members.login.domain.service;

import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import com.ohgiraffers.mtvsreserve.members.login.domain.repository.MemberRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;


    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @DisplayName("새로 생성한 멤버와 저장한 멤버가 같은지 여부 확인")
    @Test
    void save() {
        // given
        MemberDTO 태혀니 = new MemberDTO(1L, "memberName", "서버개발", "memberId", "memberPassword");

        // when
        MemberDTO savedMember = memberService.save(태혀니);

        // then
        Assertions.assertEquals(태혀니, savedMember);
    }

    @DisplayName("저장되어 있는 아이디와 새로 생성할 멤버의 아이디를 비교해서 중복이면 멤버가, 없으면 null이 나오는지 확인 ")
    @Test
    void duplicationCheckById() {
        // given
        MemberDTO 태혀니 = new MemberDTO(1L, "memberName", "서버개발", "memberId", "memberPassword");
        MemberDTO savedMember = memberService.save(태혀니);

        // when
        MemberDTO result1 = memberService.duplicationCheckById("memberId");// 기존 아이디와 중복
        MemberDTO result2 = memberService.duplicationCheckById("memberId2");// 중복 아님

        // then
        Assertions.assertEquals(result1, savedMember);
        Assertions.assertEquals(result2, null);
    }

    @DisplayName("새로 저장한 멤버들의 리스트와, 저장한 멤버를 불러오는 리스트와 비교해서 같은지 확인")
    @Test
    void findAll() {
        // given
        MemberDTO member1 = new MemberDTO(1L, "memberName1", "서버개발", "memberId1", "memberPassword1");
        MemberDTO member2 = new MemberDTO(2L, "memberName2", "서버개발", "memberId2", "memberPassword2");

        // when
        MemberDTO savedMember1 = memberService.save(member1);
        MemberDTO savedMember2 = memberService.save(member2);
        List<MemberDTO> savedMembers = Arrays.asList(savedMember1, savedMember2);

        List<MemberDTO> memberDTOS = memberService.findAll();

        // then
        Assertions.assertEquals(memberDTOS, savedMembers);
    }

    @DisplayName("새로 저장한 멤버와, 저장한 멤버의 아이디를 넣고 나오는 멤버가 같은지 확인")
    @Test
    void findByLoginId() {
        // given
        MemberDTO 태혀니 = new MemberDTO(1L, "memberName", "서버개발", "memberId", "memberPassword");

        // when
        MemberDTO savedMember = memberService.save(태혀니);
        MemberDTO memberServiceByLoginId = memberService.findByLoginId("memberId");

        // then
        Assertions.assertEquals(savedMember, memberServiceByLoginId);
    }
}