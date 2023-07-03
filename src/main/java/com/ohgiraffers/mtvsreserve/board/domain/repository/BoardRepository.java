package com.ohgiraffers.mtvsreserve.board.domain.repository;

import com.ohgiraffers.mtvsreserve.board.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}