package com.groupware.erp.vacation.controller;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.service.EmployeeService;
import com.groupware.erp.login.annualLeave.AnnualLeaveEntity;
import com.groupware.erp.login.annualLeave.AnnualLeaveService;
import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;
import com.groupware.erp.vacation.service.VacationService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("vacation")
@Log4j2
public class VacationController {

//    private static final Logger log = LoggerFactory.getLogger(VacationController.class);
    private final VacationService vacationService;
    private final AnnualLeaveService annualLeaveService;
    private final EmployeeService employeeService;

    public VacationController(VacationService vacationService, EmployeeService employeeService, AnnualLeaveService annualLeaveService) {
        this.vacationService = vacationService;
        this.employeeService = employeeService;
        this.annualLeaveService = annualLeaveService;
    }


    /* 휴가 페이지 진입 시 휴가 리스트 출력
    URL : vacation/requestList
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/requestList")
    public String requestList(@RequestParam(name = "empNo") String empNo, Model model) {
        System.out.println("requestList");
        log.info("empNo: " + empNo);

        try {
            // 휴가 기록 가져오기
            List<VacationEntity> entity = vacationService.findByEmpNoOrderByVacNoDesc(empNo);
            Optional<AnnualLeaveEntity> annOptional = annualLeaveService.findByEmpNo(empNo);
            AnnualLeaveEntity annEntity = annOptional.get();

            model.addAttribute("annEntity", annEntity);

            // List에서 가장 첫 번째 데이터만 가져오기
            if (entity != null && !entity.isEmpty()) {
                VacationEntity topEntity = entity.get(0); // 첫 번째 데이터를 가져옴
                model.addAttribute("topEntity", topEntity); // 첫 번째 데이터만 전달
            } else {
                log.info("No data found for empNo: " + empNo);
            }

            model.addAttribute("vacationEntity", entity);

        } catch (Exception e) {
            log.error(e.getMessage());
        }

        model.addAttribute("empNo", empNo);
        return "vacation/requestList";
    }


    /* 휴가 신청 클릭 시 휴가 신청 페이지 출력
    URL : vacation/requestPage
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/requestPage")
    public String requestPage (@RequestParam(value = "empNo") String empNo, Model model) {
        System.out.println("empNo: " + empNo);
        System.out.println("requestPage");
        model.addAttribute("empNo", empNo);

        Optional<EmployeeEntity> empData = employeeService.findByEmpNo(empNo);

        AnnualLeaveEntity annEntity = empData.get().getAnnualLeave();

        model.addAttribute("annEntity", annEntity);

        model.addAttribute("empName", empData.get().getEmpName());

        return "vacation/requestPage";
    }


    /* 신청 페이지에서 작성 후 신청 버튼 클릭 시 전송
    URL : vacation/requestPage
    POST 방식
    parameter : emp_no, start_date, end_date, request_reason
    * */
    @PostMapping("/requestPage")
    @ResponseBody
    public ResponseEntity<Integer> requestPostPage (@RequestBody VacationDTO dto, Model model) {
        log.info("requestPostPage called");

        String empNo = dto.getEmpNo();
        LocalDate reqDate = dto.getReqDate();
        LocalDate startDate = dto.getStartDate();
        LocalDate endDate = dto.getEndDate();
        String requestReason = dto.getRequestReason();
        int totalDays = dto.getTotalDays();

//        // startDate와 endDate를 비교해서 휴가 일수 계산
//        int totalDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;  // 시작일과 종료일을 포함하는 경우 +1
//
//        log.info("totalDays: " + totalDays);

        // 휴가 일수를 dto에 설정
        dto.setTotalDays(totalDays);

        log.info("Received vacation request: empNo={}, reqDate={}, startDate={}, endDate={}, requestReason={}, totalDays={}",
                empNo, reqDate, startDate, endDate, requestReason, totalDays);

        try {
            VacationEntity entity = vacationService.save(dto);

            if (entity == null) {
                log.error("Vacation request failed for empNo={}", empNo);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
            }

            log.info("Vacation request successful for empNo={}", empNo);
            return ResponseEntity.ok(200);

        } catch (Exception e) {
            log.error("Error processing vacation request for empNo={}: {}", empNo, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(500);
        }
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

    /* 휴가 신청 후 작성된 휴가 신청에 관한 페이지
    URL : vacation/vacationResultPage
    GET 방식
    parameter : emp_no
    * */
    @GetMapping("/vacationResultPage")
    public String vacationResultPage (Model model) {
        System.out.println("requestConfirmPagePost");
        return "vacation/vacationResultPage";
    }
}
