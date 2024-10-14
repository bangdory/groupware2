package com.groupware.erp.admin.employee.controller;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.service.AdminEmployeeService;
import com.groupware.erp.config.CustomUserDetails;
import com.groupware.erp.token.JwtTokenProvider;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/admin/employee")
public class AdminEmployeeController {

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/joinEmployee")
    public String joinEmployee(@RequestHeader("Authorization") String authorization, Model model) {
        // jwt 토큰 추출
        String token = authorization.replace("Bearer ", ""); // Bearer 제거

        if (jwtTokenProvider.validateToken(token)) {
            return "redirect:/admin/employee/joinEmployee";
        } else {
            throw new RuntimeException("Invalid token");
        }

    }

    // 신규직원 등록
    @PostMapping("/joinEmployee")
    public String joinEmployee(@ModelAttribute @Valid AdminEmployeeDetailDTO adminEmployeeDetailDTO,
                                              BindingResult bindingResult,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            return "joinEmployee";
        }
        String empNo = adminEmployeeService.joinEmployee(adminEmployeeDetailDTO);
        model.addAttribute("successMessage","신규 직원이 등록되었습니다. 사원번호: "+ empNo);
        return "redirect:/admin/employee/success"; // 등록완료페이지 리다이렉트
    }
}
