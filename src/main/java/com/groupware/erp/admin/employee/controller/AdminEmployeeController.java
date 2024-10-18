package com.groupware.erp.admin.employee.controller;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.service.AdminEmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller()
@RequestMapping(value = "/admin/employee")
public class AdminEmployeeController {

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @GetMapping("/newEmployee")
    public String joinEmployee(Model model) {
        return "newEmployee";
    }

    // 신규직원 등록
    @PostMapping("/newEmployee")
    public String joinEmployee(@ModelAttribute @Valid AdminEmployeeDetailDTO adminEmployeeDetailDTO,
                                              BindingResult bindingResult,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            return "newEmployee";
        }
        String empNo = adminEmployeeService.joinEmployee(adminEmployeeDetailDTO);
        model.addAttribute("successMessage","신규 직원이 등록되었습니다. 사원번호: "+ empNo);
        return "redirect:/admin/employee/success"; // 등록완료페이지 리다이렉트
    }
}
