package com.ohgiraffers.mtvsreserve.board;

import com.ohgiraffers.mtvsreserve.board.dto.BoardDTO;
import com.ohgiraffers.mtvsreserve.board.service.BoardService;
import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

import static com.ohgiraffers.mtvsreserve.members.login.common.session.SessionConst.LOGIN_MEMBER;

@Controller
public class BoardController {
    private BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public String list(Model model) {
        List<BoardDTO> boardDTOList = boardService.getBoardList();
        model.addAttribute("postList", boardDTOList);
        return "board/list.html";
    }
    @GetMapping("/post")
    public String post() {
        return "board/post.html";
    }

    @PostMapping("/post")
    public String write(BoardDTO boardDTO , HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(LOGIN_MEMBER);
        boardDTO.setAuthor(memberDTO.getName());
        boardService.savePost(boardDTO);
        return "redirect:/board";
    }

    // detail 조회
    @GetMapping("/post/{id}")
    public String detail(@PathVariable("id") Long id, Model model,HttpSession session) {
        BoardDTO boardDTO = boardService.getPost(id);
        model.addAttribute("post", boardDTO);

        MemberDTO memberDTO = (MemberDTO) session.getAttribute(LOGIN_MEMBER);
        String userName=memberDTO.getName();
        model.addAttribute("userName",userName);
        return "board/detail.html";
    }

    //edit 추가
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        BoardDTO boardDTO = boardService.getPost(id);
        model.addAttribute("post", boardDTO);
        return "board/edit.html";
    }

    @PutMapping("/post/edit/{id}")
    public String update(BoardDTO boardDTO, HttpSession session) {
        MemberDTO memberDTO = (MemberDTO) session.getAttribute(LOGIN_MEMBER);
        boardDTO.setAuthor(memberDTO.getName());
        boardService.savePost(boardDTO);
        return "redirect:/board";
    }

    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id) {
        boardService.deletePost(id);
        return "redirect:/board";
    }

}
