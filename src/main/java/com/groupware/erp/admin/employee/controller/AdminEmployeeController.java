package com.groupware.erp.admin.employee.controller;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.service.AdminEmployeeService;
import com.groupware.erp.admin.employee.service.EmployeeUtils;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping(value = "/admin/employee")
public class AdminEmployeeController {

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @GetMapping("/newEmployee")
    public String newEmployee(Model model) {
        return "admin/employee/newEmployee";
    }

    // 신규직원 등록
    @PostMapping("/newEmployee")
    public String newEmployee(@ModelAttribute @Valid AdminEmployeeDetailDTO adminEmployeeDetailDTO,
                                              BindingResult bindingResult,
                                              Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/employee/newEmployee";
        }
        String empNo = adminEmployeeService.newEmployee(adminEmployeeDetailDTO);
        model.addAttribute("successMessage","신규 직원이 등록되었습니다. 사원번호: "+ empNo);
        return "redirect:/admin/employee/success"; // 등록완료페이지 리다이렉트
    }

    // 회원목록Page
    @GetMapping
    public String listForm(Model model) {
        List<AdminEmployeeDetailDTO> employeeList = adminEmployeeService.findAll();
        model.addAttribute("employeeList", employeeList);
        return "/admin/employee";
    }

//    // 회원상세Page
//    @GetMapping("/detail/{empNo}")
//    public String findById(@PathVariable Long empNo, Model model) {
//        AdminEmployeeDetailDTO adminEmployeeDetailDTO = adminEmployeeService.findByNo(empNo);
//        model.addAttribute("employee", adminEmployeeDetailDTO);
//        return "/admin/employee/detail";
//    }
//
//    // 회원수정Page
//    @GetMapping("/update/{empNo}")
//    public String updateForm(@PathVariable Long empNo, Model model, HttpSession session) {
//        AdminEmployeeDetailDTO adminEmployeeDetailDTO = adminEmployeeService.findByNo(empNo);
//        model.addAttribute("employee", adminEmployeeDetailDTO);
//        return "/admin/employee/update";
//    }
//
//    // 회원수정
//    @PutMapping("/updateEmployee/{empNo}")
//    public ResponseEntity employeeUpdate(@RequestBody AdminEmployeeDetailDTO adminEmployeeDetailDTO) {
//        Long empNo = adminEmployeeService.update(adminEmployeeDetailDTO);
//        return new ResponseEntity(HttpStatus.OK);
//    }
//
//    // 회원삭제
//    @PostMapping("/delete/{empNo}")
//    public ResponseEntity deleteById(@PathVariable("empNo") Long empNo) {
//        adminEmployeeService.deleteByNo(empNo);
//        return new ResponseEntity(HttpStatus.OK);
//    }
}
