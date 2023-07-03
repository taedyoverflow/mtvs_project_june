package com.ohgiraffers.mtvsreserve.reservation.controller;

import com.ohgiraffers.mtvsreserve.members.login.application.dto.MemberDTO;
import com.ohgiraffers.mtvsreserve.reservation.dto.TableInfoDTO;
import com.ohgiraffers.mtvsreserve.reservation.dto.TimeListDTO;
import com.ohgiraffers.mtvsreserve.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.ohgiraffers.mtvsreserve.members.login.common.session.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;
    @GetMapping("doreserve")
    public String doReservePage(@RequestParam String roomNum ,Model model){
        model.addAttribute("roomNum",roomNum);
        String date=reservationService.CurrentDay();
        model.addAttribute("today",date);
        String fdate=reservationService.maxDay();
        model.addAttribute("maxdate",fdate);
        reservationService.deleteBeforeDate();
        return "/reservation/doreserve";
    }
    @PostMapping("doreserve")
    public String doReserve(@RequestParam String roomNum, @RequestParam String date, Model model ){
        model.addAttribute("roomNum",roomNum);
        model.addAttribute("date",date);

        List<TableInfoDTO> roomlist= reservationService.findCompleteReserve(date, roomNum);
        model.addAttribute("roomlist",roomlist);
        List<TimeListDTO> timelist =reservationService.timeList();
        model.addAttribute("timelist",timelist);
        return "reservation/doreservetime";
    }
    @PostMapping("doreserve1")
    public String doReserve1(@ModelAttribute TableInfoDTO tableInfoDTO
            , HttpServletRequest req, HttpServletResponse response, HttpSession session
    ) throws IOException {

        MemberDTO memberDTO = (MemberDTO) session.getAttribute(LOGIN_MEMBER);
        tableInfoDTO.setUserId(memberDTO.getName());
        reservationService.save(tableInfoDTO,response);
        return "reservation/viewreserve";
    }

    @GetMapping("/mypage")
    public String checkReservation(Model model, HttpSession session){

        MemberDTO memberDTO = (MemberDTO) session.getAttribute(LOGIN_MEMBER);
        model.addAttribute("user",memberDTO);

        List<TableInfoDTO> tableInfoDTO = reservationService.viewAllReservation(memberDTO.getName());
        model.addAttribute("infos", tableInfoDTO);

        List<TimeListDTO> timelist =reservationService.timeList();
        model.addAttribute("timelist",timelist);

        return "mypage";
    }

    @GetMapping("deletereservationinfo/{id}")
    public String deleteReservationInfo(@PathVariable Long id){
        reservationService.deleteById(id);
        return "redirect:/mypage";
    }
}
