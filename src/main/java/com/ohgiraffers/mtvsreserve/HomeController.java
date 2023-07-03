package com.ohgiraffers.mtvsreserve;

import com.ohgiraffers.mtvsreserve.board.dto.BoardDTO;
import com.ohgiraffers.mtvsreserve.board.service.BoardService;
import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import com.ohgiraffers.mtvsreserve.members.login.common.argumentresolver.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    @GetMapping("/")
    public String homeLoginArgumentResolver(@Login MemberDTO loginMember, Model model) {
        try {
            List<BoardDTO> boardDTOList = boardService.getBoardList();
            List<BoardDTO> finalBoardList = new ArrayList<>();
            if(boardDTOList.size() < 3){
                for (int i=0; i<boardDTOList.size(); i++){
                    finalBoardList.add(boardDTOList.get(i));
                }
            }else{
                for (int i=0; i<3; i++){
                    finalBoardList.add(boardDTOList.get(i));
                }
            }
            model.addAttribute("boardList", finalBoardList);
        }catch (Exception e){
            e.printStackTrace();
        }
        //세션에 회원 데이터가 없으면 home
        if (loginMember == null) {
            return "main";
        }
        //세션이 유지되면 로그인으로 이동
        model.addAttribute("member", loginMember);
        return "loginMain";
    }

    @GetMapping("/reservation")
    public String reserve(){
        return "reservation/viewreserve";
    }
}