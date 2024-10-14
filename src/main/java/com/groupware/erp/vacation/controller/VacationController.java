package com.groupware.erp.vacation.controller;

import com.groupware.erp.token.JwtTokenProvider;
import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.service.VacationService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("vacation")
public class VacationController {

    private static final Logger log = LoggerFactory.getLogger(VacationController.class);
    private final VacationService vacationService;

    private final JwtTokenProvider jwtTokenProvider;

    public VacationController(VacationService vacationService, JwtTokenProvider jwtTokenProvider) {
        this.vacationService = vacationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    /* 휴가 페이지 진입 시 휴가 리스트 출력
    URL : vacation/requestList
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/requestList")
    public String requestList(HttpServletRequest request, Model model) {
        String token = jwtTokenProvider.resolveToken(request);
        String test = jwtTokenProvider.getUsernameFromToken(token);
        log.info("emp_no: " + test);
        System.out.println("requestList");

        model.addAttribute("emp_no", "0000000000");
        return "vacation/requestList";
    }


    /* 휴가 신청 클릭 시 휴가 신청 페이지 출력
    URL : vacation/requestPage
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/requestPage")
    public String requestPage (@RequestParam(value = "emp_no") String emp_no, Model model) {
        System.out.println("emp_no: " + emp_no);
        System.out.println("requestPage");
        model.addAttribute("emp_no", emp_no);
        return "vacation/requestPage";
    }


    /* 신청 페이지에서 작성 후 신청 버튼 클릭 시 전송
    URL : vacation/requestPage
    POST 방식
    parameter : emp_no, start_date, end_date, request_reason
    * */
    @PostMapping("/requestPage")
    public String requestPostPage (Model model) {
        System.out.println("requestPostPage");
        return "vacation/requestList";
    }


    // 상급자 휴가 관리

    /* 휴가 관리 클릭 시 신청 온 휴가 리스트 출력
    URL : vacation/requestConfirmList.html
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/requestConfirmList")
    public String requestConfirmList (Model model) {
        System.out.println("requestConfirmList");
        return "vacation/requestConfirmList";
    }



    /* 신청 온 휴가 리스트에서 클릭 시 해당 휴가 신청서 페이지 출력
    URL : vacation/requestConfirmPage
    GET 방식
    parameter : emp_no, vac_no
    * */
    @GetMapping("/requestConfirmPage")
    public String requestConfirmPage (Model model) {
        System.out.println("requestConfirmPage");
        return "vacation/requestConfirmPage";
    }



    /* 특정 휴가 페이지에서 승인 또는 거절 처리 (거절 시 거절 사유 첨부)
    URL : vacation/requestConfirmPage
    POST 방식
    parameter : emp_no, approve_boolean, authorizer(emp_no), rejection_reason
    * */
    @PostMapping("/requestConfirmPage")
    public String requestConfirmPagePost (Model model) {
        System.out.println("requestConfirmPagePost");
        return "vacation/requestConfirmList";
    }
}
