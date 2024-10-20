package com.groupware.erp.admin.controller;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.entity.AdminEmployeeEntity;
import com.groupware.erp.admin.repository.AdminEmployeeRepository;
import com.groupware.erp.admin.service.AdminEmployeeService;
import com.groupware.erp.attendance.domain.AttendanceEntity;
import com.groupware.erp.attendance.service.AttendanceService;
import com.groupware.erp.login.LoginService;
import com.groupware.erp.token.JwtTokenProvider;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/admin")
public class AdminEmployeeController {

    private static final Logger log = LoggerFactory.getLogger(AdminEmployeeController.class);

    @Autowired
    private AdminEmployeeService adminEmployeeService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private LoginService loginService;
    @Autowired
    private AdminEmployeeRepository adminEmployeeRepository;

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
        return "redirect:/admin/adminEmployee"; // 등록완료
    }

    @GetMapping("/employeeList")
    public String employeeList(@RequestParam(name = "empNo") String empNo, Model model) {
        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();
        // 출근/퇴근 데이터를 가져옴
        Optional<AttendanceEntity> attendanceList = attendanceService.findByEmpNoAndRegDate(empNo, today);
        List<AdminEmployeeEntity> adminEmployeeEntity = adminEmployeeService.getEmployees();

        model.addAttribute("adminEmployeeEntity", adminEmployeeEntity);
        model.addAttribute("attendanceList", attendanceList);
        return "admin/employeeList";
    }

    @GetMapping("/adminEmployee")
    public String adminEmployee(Model model) {
        List<AdminEmployeeEntity> adminEmployeeEntity = adminEmployeeService.getEmployees();

        model.addAttribute("adminEmployeeEntity", adminEmployeeEntity);
        return "admin/adminEmployee";
    }

    @GetMapping("/editEmployee")
    public String editEmployee(@RequestParam String empNo, Model model) {
        Optional<AdminEmployeeEntity> findByEmpNo = adminEmployeeRepository.findByEmpNo(empNo);

        if(findByEmpNo.isPresent()) {
            AdminEmployeeEntity adminEmployeeEntity = findByEmpNo.get();
            log.info("editEmployee 실행{},{},{},{}",
                    adminEmployeeEntity.getEmpNo(),
                    adminEmployeeEntity.getEmpPassword(),
                    adminEmployeeEntity.getEmpName(),
                    adminEmployeeEntity.getEmpEmail());
            model.addAttribute("adminEmployeeEntity", adminEmployeeEntity);
        } else {
            log.warn("employee not found{}", empNo);
            return "redirect:/admin/adminEmployee";
        }
        return "admin/editEmployee";
    }

    @PostMapping("/editEmployee")
    public ModelAndView editEmployee(@ModelAttribute @Valid AdminEmployeeEntity adminEmployeeEntity) {

        adminEmployeeService.updateEmployee(adminEmployeeEntity);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/adminEmployee");
        return modelAndView;
    }

    @PostMapping("/deleteEmployee")
    public ResponseEntity<Void> deleteEmployee(@RequestParam(name = "empNo") String empNo) {

        log.info("실행했음 {}", empNo);

        try {
            adminEmployeeService.updateEmployeeStatus(empNo, "퇴사");
            return ResponseEntity.ok().build();
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
