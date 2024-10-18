package com.groupware.erp.admin.controller;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.service.AdminEmployeeService;
import com.groupware.erp.login.LoginService;
import com.groupware.erp.token.JwtTokenProvider;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin")
public class AdminEmployeeController {

    private static final Logger log = LoggerFactory.getLogger(AdminEmployeeController.class);

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private LoginService loginService;

    @GetMapping("/joinEmployee")
    public String joinEmployee() { //@RequestHeader("Authorization") String authorization, Model model
//        // jwt 토큰 추출
//        String token = authorization.replace("Bearer ", ""); // Bearer 제거
//
//        if (jwtTokenProvider.validateToken(token)) {
//            return "redirect:/admin/joinEmployee";
//        } else {
//            throw new RuntimeException("Invalid token");
//        }

        return "admin/joinEmployee";
    }

    // 신규직원 등록
    @PostMapping("/joinEmployee")
    public String joinEmployee(@ModelAttribute @Valid AdminEmployeeDetailDTO adminEmployeeDetailDTO,
                                              BindingResult bindingResult,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            return "joinEmployee";
        }

        log.info("html에서 다 받아왔습니까?!??{}", adminEmployeeDetailDTO);

        String empNo = adminEmployeeService.joinEmployee(adminEmployeeDetailDTO);
        model.addAttribute("successMessage","신규 직원이 등록되었습니다. 사원번호: "+ empNo);
        return "redirect:/admin/joinEmployee"; // 등록완료 , 관리자메뉴 페이지 구현 다 되면 경로 변경할 것.
    }
}
