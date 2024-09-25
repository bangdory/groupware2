package com.groupware.erp.admin.employee.controller;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.service.AdminEmployeeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping(value = "/admin/member")
public class AdminEmployeeController {

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    // 회원목록Page
    @GetMapping("/list")
    public String listForm(Model model) {
        List<AdminEmployeeDetailDTO> employeeList = adminEmployeeService.findAll();
        model.addAttribute("employeeList", employeeList);
        return "/employee/list";
    }

    // 회원상세Page
    @GetMapping("/detail/{empNo}")
    public String findById(@PathVariable Long empNo, Model model) {
        AdminEmployeeDetailDTO adminEmployeeDetailDTO = adminEmployeeService.findByNo(empNo);
        model.addAttribute("employee", adminEmployeeDetailDTO);
        return "/admin/employee/detail";
    }

    // 회원수정Page
    @GetMapping("/update/{empNo}")
    public String updateForm(@PathVariable Long empNo, Model model, HttpSession session) {
        AdminEmployeeDetailDTO adminEmployeeDetailDTO = adminEmployeeService.findByNo(empNo);
        model.addAttribute("employee", adminEmployeeDetailDTO);
        return "/admin/employee/update";
    }

    // 회원수정
    @PutMapping("/updateEmployee/{empNo}")
    public ResponseEntity memberUpdate(@RequestBody AdminEmployeeDetailDTO adminEmployeeDetailDTO) {
        Long empNo = adminEmployeeService.update(adminEmployeeDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 회원삭제
    @PostMapping("/delete/{empNo}")
    public ResponseEntity deleteById(@PathVariable("empNo") Long empNo) {
        adminEmployeeService.deleteByNo(empNo);
        return new ResponseEntity(HttpStatus.OK);
    }
}
