package com.groupware.erp.employee.controller;

import com.groupware.erp.employee.dto.EmployeeJoinDTO;
import com.groupware.erp.employee.dto.EmployeeMapperDTO;
import com.groupware.erp.employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    // 회원가입Page
//    @GetMapping("/join")
//    public String joinForm() {
//        return "/employee/join";
//    }

    // 회원가입 처리
//    @PostMapping("/employeeJoin")
//    public String employeeJoin(@ModelAttribute EmployeeJoinDTO employeeJoinDTO) {
//        Long empNo = employeeService.join(employeeJoinDTO);
//        return "/";
//    }

//     로그인Page
//    @GetMapping("/login")
//    public String loginForm() {
//        return "/login";
//    }

    // 회원목록Page
    @GetMapping("/list")
    public String listForm(Model model) {
        EmployeeMapperDTO paramEmployeeMapperDTO = new EmployeeMapperDTO();
        paramEmployeeMapperDTO.setEmpEmail("email.com");
        paramEmployeeMapperDTO.setEmpName("peter");

        List<EmployeeMapperDTO> employeeList = employeeService.employeeList(paramEmployeeMapperDTO);
        model.addAttribute("employeeList", employeeList);
        return "/employee/list";
    }
}
