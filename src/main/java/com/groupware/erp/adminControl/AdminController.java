package com.groupware.erp.adminControl;

import com.groupware.erp.attendance.domain.AttendanceEntity;
import com.groupware.erp.attendance.service.AttendanceService;
import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.service.EmployeeService;
import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;
import com.groupware.erp.vacation.service.VacationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.util.*;

@Controller
@RequestMapping("/edit")
@Log4j2
public class AdminController {


    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private VacationService vacationService;

    @GetMapping("/empAdmin")
    public String empAdmin(@RequestParam(name = "empNo") String empNo, Model model) {
        log.info("empNo: " + empNo);

        Optional<EmployeeEntity> empEntityOpt = employeeService.findByEmpNo(empNo);
        Optional<VacationEntity> vacEntityOpt = vacationService.findLatestByEmpNo(empNo);

        EmployeeEntity empEntity = empEntityOpt.orElse(null);
        VacationEntity vacEntity = vacEntityOpt.orElse(null);

        log.info("empNo: " + empNo);

        // Attendance 데이터를 5개만 가져옴
        List<AttendanceEntity> attEntity = attendanceService.findTop5ByEmpNoOrderByAttNoDesc(empNo);

        log.info("Employee Entity: " + empEntity.toString());
        log.info("Vacation Entity: " + vacEntity.toString());
        for (AttendanceEntity att : attEntity) {
            log.info("Attendance Entity: " + att.toString());
        }

        // 모델에 추가
        model.addAttribute("empEntity", empEntity);
        model.addAttribute("vacEntity", vacEntity);
        model.addAttribute("attEntity", attEntity);

        return "edit/empAdmin";
    }

    @PostMapping("/editStartTime")
    public ResponseEntity<?> editStartTime(@RequestBody Map<String, String> requestData) {

        try {
            Long attNo = Long.valueOf(requestData.get("attNo"));
            String hour = requestData.get("hour");
            String minute = requestData.get("minute");

            // 시간을 처리할 로직 작성
            Time startTime = Time.valueOf(hour + ":" + minute + ":00");

            AttendanceEntity attEntity = attendanceService.findByAttNo(attNo);
            attEntity.setArrTime(startTime);

            attendanceService.updateStartTime(attEntity);

            String updateArrTime = String.valueOf(attEntity.getArrTime());

            // 성공 메시지를 JSON 형식으로 반환
            Map<String, String> response = new HashMap<>();
            response.put("arrTime", updateArrTime);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }
    }


    @PostMapping("/editEndTime")
    public ResponseEntity<?> editEndTime(@RequestBody Map<String, String> requestData) {

        try {
            Long attNo = Long.valueOf(requestData.get("attNo"));
            String hour = requestData.get("hour");
            String minute = requestData.get("minute");

            // 시간을 처리할 로직 작성
            Time endTime = Time.valueOf(hour + ":" + minute + ":00");

            AttendanceEntity attEntity = attendanceService.findByAttNo(attNo);
            attEntity.setLevTime(endTime);

            attendanceService.updateStartTime(attEntity);

            String updateLevTime = String.valueOf(attEntity.getLevTime());

            // 성공 메시지를 JSON 형식으로 반환
            Map<String, String> response = new HashMap<>();
            response.put("levTime", updateLevTime);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/approve")
    public ResponseEntity<?> approve(@RequestBody Map<String , Integer> requestDate) {
        try{
        int vacNo = requestDate.get("vacNo");
        int approve = requestDate.get("approve");

        VacationEntity vacationEntity = vacationService.findByVacNo(vacNo);
        vacationEntity.setApproveBoolean(approve);

         int result = vacationService.updateVacationApprove(vacNo, approve);
            // 성공 메시지를 JSON 형식으로 반환

            Map<String, Integer> response = new HashMap<>();
            response.put("updateApprove", result);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).body(Collections.singletonMap("error", e.getMessage()));
        }

    }
}
