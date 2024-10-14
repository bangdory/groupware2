package com.groupware.erp.attendance.controller;

import com.groupware.erp.attendance.domain.AttendanceEntity;
import com.groupware.erp.attendance.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    /* 근태 페이지 접속 시 리스트 페이지 출력
    URL : attendance/attList
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/attList")
    public String attList () {
        return "attendance/attList";
    }


    /* 근태 페이지에서 출근 클릭 시 출근 비동기 처리
    URL : attendance/checkIn
    POST 방식
    parameter : emp_no
    * */
    @PostMapping("/checkIn")
    @ResponseBody
    public ResponseEntity<AttendanceEntity> checkIn (@RequestParam("emp_no") int emp_no) {
        return null;
    }

    /* 근태 페이지에서 퇴근 클릭 시 출근 비동기 처리
    URL : attendance/checkOut
    POST 방식
    parameter : emp_no
    * */
    @PostMapping("/checkOut")
    @ResponseBody
    public ResponseEntity<AttendanceEntity> checkOut (@RequestParam("emp_no") int emp_no) {
        return null;
    }
}
