package com.ohgiraffers.mtvsreserve.members.login.domain.service;

import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import com.ohgiraffers.mtvsreserve.members.login.domain.repository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LoginServiceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private LoginService loginService;

    @AfterEach
    void clear() {
        memberRepository.deleteAll();
    }

    @DisplayName("저장한 멤버의 아이디와 비밀번호가 같으면 멤버 객체가, 아이디나 비밀번호 다르면 null이 나오는지 확인")
    @Test
    void login() {

        // given
        MemberDTO 태혀니 = new MemberDTO(1L, "김태현", "서버개발", "taehyun", "apxkqjtmdkzkepal");
        memberService.save(태혀니);

        // when
        MemberDTO result1 = loginService.login("taehyun", "apxkqjtmdkzkepal");
        MemberDTO result2 = loginService.login("taehyunn", "apxkqjtmdkzkepal");
        MemberDTO result3 = loginService.login("taehyun", "apapapapapdfhke");

        // then
        Assertions.assertEquals(태혀니, result1);
        Assertions.assertEquals(null, result2);
        Assertions.assertEquals(null, result3);
    }
}