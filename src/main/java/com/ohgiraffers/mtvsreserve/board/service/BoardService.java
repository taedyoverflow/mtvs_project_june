package com.ohgiraffers.mtvsreserve.board.service;


import com.ohgiraffers.mtvsreserve.board.domain.repository.BoardRepository;
import com.ohgiraffers.mtvsreserve.board.domain.entity.Board;
import com.ohgiraffers.mtvsreserve.board.dto.BoardDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    private BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Long savePost(BoardDTO boardDTO) {
        return boardRepository.save(boardDTO.toEntity()).getId();
    }

    @Transactional
    public List<BoardDTO> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();

        for (Board board : boardList) {
            BoardDTO boardDto = BoardDTO.builder()
                    .id(board.getId())
                    .author(board.getAuthor())
                    .title(board.getTitle())
                    .content(board.getContent())
                    .createdDate(board.getCreatedDate())
                    .build();

            boardDTOList.add(boardDto);
        }
        return boardDTOList;
    }

    //detail 추가
    @Transactional
    public BoardDTO getPost(Long id) {
        Board board = boardRepository.findById(id).get();

        BoardDTO boardDTO = BoardDTO.builder()
                .id(board.getId())
                .author(board.getAuthor())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDate(board.getCreatedDate())
                .build();
        return boardDTO;
    }

    //삭제 기능
    @Transactional
    public void deletePost(Long id) {
        boardRepository.deleteById(id);
    }
}