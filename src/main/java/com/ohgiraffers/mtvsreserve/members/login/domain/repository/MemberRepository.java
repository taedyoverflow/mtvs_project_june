package com.ohgiraffers.mtvsreserve.members.login.domain.repository;

import com.ohgiraffers.mtvsreserve.members.login.domain.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByLoginId(String LoginId);
}
