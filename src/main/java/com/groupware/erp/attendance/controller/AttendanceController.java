package com.groupware.erp.attendance.controller;

import com.groupware.erp.attendance.domain.AttendanceEntity;
import com.groupware.erp.attendance.dto.AttendanceVO;
import com.groupware.erp.attendance.service.AttendanceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Time;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("attendance")
public class AttendanceController {

    private static final Logger log = LoggerFactory.getLogger(AttendanceController.class);
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
    public String attList(@RequestParam(name = "empNo") String empNo, Model model) {
        log.info("empNo: " + empNo);

        // 출근/퇴근 데이터를 가져옴
        List<AttendanceEntity> attendanceList = attendanceService.getAttendanceListByEmpNo(empNo);

        // 오늘 날짜 가져오기
        LocalDate today = LocalDate.now();

        if (attendanceList.isEmpty()) {
            // 데이터가 하나도 없으면 미출근으로 처리
            AttendanceEntity emptyAttendance = new AttendanceEntity();
            emptyAttendance.setEmpNo(empNo);
            emptyAttendance.setRegDate(today);
            emptyAttendance.setAttStat("미출근");
            attendanceList.add(emptyAttendance);
            // 빈 데이터 저장
            attendanceService.EmptyDataSave(emptyAttendance);
        }

            // 가장 최신의 데이터를 가져옴
            AttendanceEntity mostRecentAttendance = attendanceList.stream()
                    .max(Comparator.comparing(AttendanceEntity::getRegDate))
                    .orElse(null);

            if (mostRecentAttendance != null) {
                LocalDate latestDate = mostRecentAttendance.getRegDate();

                if (latestDate.isBefore(today)) {
                    // 최신 데이터와 오늘 사이의 공백 날짜들을 미출근으로 처리
                    LocalDate missingDate = latestDate.plusDays(1);
                    while (missingDate.isBefore(today)) {
                        AttendanceEntity missingAttendance = new AttendanceEntity();
                        missingAttendance.setEmpNo(empNo);
                        missingAttendance.setRegDate(missingDate);
                        missingAttendance.setAttStat("미출근");
                        attendanceList.add(missingAttendance);
                        attendanceService.EmptyDataSave(missingAttendance);
                        missingDate = missingDate.plusDays(1);
                    }
                    AttendanceEntity todayAttendance = new AttendanceEntity();
                    todayAttendance.setEmpNo(empNo);
                    todayAttendance.setRegDate(today);
                    todayAttendance.setAttStat("미출근");
                    attendanceService.EmptyDataSave(todayAttendance);
                    model.addAttribute("todayAttendance", todayAttendance);
                    attendanceList.add(0, todayAttendance);
                }

                if (latestDate.isEqual(today)) {
                    model.addAttribute("todayAttendance", mostRecentAttendance);
                }
            }


        // 최신 7개의 데이터를 추출
        List<AttendanceEntity> latestSevenAttendances = attendanceList.stream()
                .sorted(Comparator.comparing(AttendanceEntity::getRegDate).reversed())
                .limit(7)
                .collect(Collectors.toList());

        // 출근, 퇴근, 미출근, 휴가 상태의 개수 카운트
        long countCheckIn = latestSevenAttendances.stream().filter(a -> "출근".equals(a.getAttStat())).count();
        long countCheckOut = latestSevenAttendances.stream().filter(a -> "퇴근".equals(a.getAttStat())).count();
        long countAbsent = latestSevenAttendances.stream().filter(a -> "미출근".equals(a.getAttStat())).count();
        long countVacation = latestSevenAttendances.stream().filter(a -> "휴가".equals(a.getAttStat())).count();

        // 카운트 데이터를 모델에 담기
        model.addAttribute("countCheckIn", countCheckIn);
        model.addAttribute("countCheckOut", countCheckOut);
        model.addAttribute("countAbsent", countAbsent);
        model.addAttribute("countVacation", countVacation);

        // 최종적으로 추가된 데이터를 모델에 전달
        model.addAttribute("attendanceList", attendanceList);
        model.addAttribute("mostRecentAttendance", mostRecentAttendance);
        model.addAttribute("empNo", empNo);

        log.info("attendanceList: " + attendanceList);
        System.out.println("--------------------------------");
        attendanceList.forEach(attendance -> log.info("AttendanceEntity: empNo=" + attendance.getEmpNo()
                + ", regDate=" + attendance.getRegDate()
                + ", attStat=" + attendance.getAttStat()
                + ", arrTime=" + attendance.getArrTime()
                + ", levTime=" + attendance.getLevTime()));



        return "attendance/attList";
    }

//    @GetMapping("/attList")
//    public String attList(@RequestParam(name = "empNo") String empNo, Model model) {
//        System.out.println("attList");
//        log.info("empNo: " + empNo);
//
//        // 출근/퇴근 데이터를 담을 리스트 객체 생성
//        List<AttendanceEntity> attendanceList = new ArrayList<>();
//
//        // 출근/퇴근 데이터를 가져옴
//        attendanceList = attendanceService.getAttendanceListByEmpNo(empNo);
//
//        // 오늘 날짜 가져오기
//        LocalDate today = LocalDate.now();
//
//
//        if (attendanceList.isEmpty()) {
//            // 데이터가 하나도 없으면 미출근으로 처리
//            AttendanceEntity emptyAttendance = new AttendanceEntity();
//            emptyAttendance.setEmpNo(empNo);
//            emptyAttendance.setRegDate(today);
//            emptyAttendance.setAttStat("미출근");
//            attendanceList.add(emptyAttendance);
//
//            model.addAttribute("status", "미출근");
////            model.addAttribute("checkInTime", "미정");
////            model.addAttribute("checkOutTime", "미정");
//
//
//            // 빈 데이터 저장
//            attendanceService.EmptyDataSave(emptyAttendance); // 새롭게 추가한 부분
//            attendanceList.add(emptyAttendance);
//        } else {
//            // 가장 최신의 데이터를 가져옴
//            AttendanceEntity mostRecentAttendance = attendanceList.stream()
//                    .max(Comparator.comparing(AttendanceEntity::getRegDate))
//                    .orElse(null);
//
//            if (mostRecentAttendance != null) {
//                LocalDate latestDate = mostRecentAttendance.getRegDate();
//
//                // 최신 데이터가 오늘보다 이전이라면
//                if (latestDate.isBefore(today)) {
//                    // 최신 데이터와 오늘 사이의 공백 날짜들을 미출근으로 처리
//                    LocalDate missingDate = latestDate.plusDays(1);
//
//                    // 최신 날짜 이후부터 오늘 이전까지 미출근 처리
//                    while (missingDate.isBefore(today)) {
//                        AttendanceEntity missingAttendance = new AttendanceEntity();
//                        missingAttendance.setEmpNo(empNo);
//                        missingAttendance.setRegDate(missingDate);
//                        missingAttendance.setAttStat("미출근");
//                        attendanceList.add(missingAttendance);
//
//                        // 추가된 '미출근' 데이터를 데이터베이스에 저장
//                        attendanceService.EmptyDataSave(missingAttendance); // 새롭게 추가한 부분
//                        missingDate = missingDate.plusDays(1);
//                    }
//
//                    // 오늘 날짜의 출근 기록이 없으면 미출근 처리
//                    AttendanceEntity todayAttendance = new AttendanceEntity();
//                    todayAttendance.setEmpNo(empNo);
//                    todayAttendance.setRegDate(today);
//                    todayAttendance.setAttStat("미출근");
//                    attendanceList.add(todayAttendance);
//
//                    model.addAttribute("status", "미출근");
//                    model.addAttribute("checkInTime", "미정");
//                    model.addAttribute("checkOutTime", "미정");
//
//                    // 오늘 날짜의 미출근 데이터 저장
//                    attendanceService.EmptyDataSave(todayAttendance); // 새롭게 추가한 부분
//                } else {
//                    // 최신 데이터가 오늘일 경우 처리
//                    if (mostRecentAttendance.getArrTime() == null && mostRecentAttendance.getLevTime() == null) {
//                        // 출근/퇴근 기록이 없으면 '미출근'
//                        model.addAttribute("status", "미출근");
//                        model.addAttribute("checkInTime", "미정");
//                        model.addAttribute("checkOutTime", "미정");
//                    } else if (mostRecentAttendance.getArrTime() != null && mostRecentAttendance.getLevTime() == null) {
//                        // 출근 기록만 있으면 '출근'
//                        model.addAttribute("status", "출근");
//                        model.addAttribute("checkInTime", mostRecentAttendance.getArrTime().toString());
//                        model.addAttribute("checkOutTime", "미정");
//                    } else if (mostRecentAttendance.getArrTime() != null && mostRecentAttendance.getLevTime() != null) {
//                        // 출근/퇴근 기록이 모두 있으면 '퇴근'
//                        model.addAttribute("status", "퇴근");
//                        model.addAttribute("checkInTime", mostRecentAttendance.getArrTime().toString());
//                        model.addAttribute("checkOutTime", mostRecentAttendance.getLevTime().toString());
//                    }
//                    // 가장 최신의 데이터를 리스트에 추가
//                    model.addAttribute("mostRecentAttendance", mostRecentAttendance);
//                    attendanceList.add(mostRecentAttendance);
//                }
//            }
//        }
//
//
//        // 최신 7개의 데이터를 추출 (regDate 기준으로 내림차순 정렬 후 상위 7개)
//        List<AttendanceEntity> latestSevenAttendances = attendanceList.stream()
//                .sorted(Comparator.comparing(AttendanceEntity::getRegDate).reversed())
//                .limit(7)
//                .collect(Collectors.toList());
//
//        // att_stat 값에 따른 출근, 미출근, 퇴근, 휴가 상태의 개수를 카운트
//        long countCheckIn = latestSevenAttendances.stream().filter(a -> "출근".equals(a.getAttStat())).count();
//        long countCheckOut = latestSevenAttendances.stream().filter(a -> "퇴근".equals(a.getAttStat())).count();
//        long countAbsent = latestSevenAttendances.stream().filter(a -> "미출근".equals(a.getAttStat())).count();
//        long countVacation = latestSevenAttendances.stream().filter(a -> "휴가".equals(a.getAttStat())).count();
//
//        // 카운트한 데이터를 모델에 담기
//        model.addAttribute("countCheckIn", countCheckIn);
//        model.addAttribute("countCheckOut", countCheckOut);
//        model.addAttribute("countAbsent", countAbsent);
//        model.addAttribute("countVacation", countVacation);
//
//        // 최종적으로 추가된 데이터를 모델에 전달
//        model.addAttribute("attendanceList", attendanceList);
//        model.addAttribute("empNo", empNo);
//
//        return "attendance/attList";
//    }






    /* 근태 페이지에서 출근 클릭 시 출근 비동기 처리
    URL : attendance/checkIn
    POST 방식
    parameter : emp_no
    * */
    @PostMapping("/checkIn")
    @ResponseBody
    public ResponseEntity<AttendanceVO> checkIn(
            @RequestParam(name = "empNo") String empNo) {
        try {
            log.info("checkIn");
            log.info("empNo: " + empNo);

            // 오늘 날짜 가져오기
            LocalDate today = LocalDate.now();

            // 기존에 오늘 날짜로 출근 기록이 있는지 확인
            Optional<AttendanceEntity> existingAttendance = attendanceService.findByEmpNoAndRegDate(empNo, today);

            AttendanceEntity entity;

            if (existingAttendance.isPresent()) {
                // 기존 기록이 있으면 해당 엔티티를 가져와서 업데이트
                entity = existingAttendance.get();
                entity.setArrTime(Time.valueOf(LocalTime.now()));
                entity.setAttStat("출근");
            } else {
                // 기존 기록이 없으면 새로운 엔티티 생성
                entity = new AttendanceEntity();
                entity.setEmpNo(empNo);
                entity.setRegDate(today);
                entity.setArrTime(Time.valueOf(LocalTime.now()));
                entity.setAttStat("출근");
            }

            // 엔티티를 저장 (insert 또는 update 처리)
            AttendanceVO result = attendanceService.attUpdate(entity);

            if (result == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            return ResponseEntity.ok(result);

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /* 근태 페이지에서 퇴근 클릭 시 출근 비동기 처리
    URL : attendance/checkOut
    POST 방식
    parameter : emp_no
    * */
    @PostMapping("/checkOut")
    @ResponseBody
    public ResponseEntity<AttendanceVO> checkOut(@RequestParam("empNo") String empNo) {
        try {
            log.info("checkOut");
            log.info("empNo: " + empNo);

            // 오늘 날짜 가져오기
            LocalDate today = LocalDate.now();

            Optional<AttendanceEntity> entity = attendanceService.findByEmpNoAndRegDate(empNo, today);

            if (entity.isPresent()) {
                AttendanceEntity attendanceEntity = entity.get();

                // 출근 시간이 있는지 먼저 확인
                if (attendanceEntity.getArrTime() == null) {
                    // 출근 시간이 없는 경우 오류 반환
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }

                // 퇴근 시간 설정
                Time leaveTime = Time.valueOf(LocalTime.now());
                attendanceEntity.setLevTime(leaveTime);
                attendanceEntity.setAttStat("퇴근");

                // 근무 시간 계산 (출근 시간과 퇴근 시간 차이)
                Time arriveTime = attendanceEntity.getArrTime();
                LocalTime arrLocalTime = arriveTime.toLocalTime();
                LocalTime levLocalTime = leaveTime.toLocalTime();

                // Duration 사용하여 차이 계산
                Duration duration = Duration.between(arrLocalTime, levLocalTime);

                // 근무 시간을 '00:00:00' 형식의 Time 타입으로 변환
                long seconds = duration.getSeconds();
                long hours = seconds / 3600;
                long minutes = (seconds % 3600) / 60;
                long secs = seconds % 60;

                // Time 타입으로 변환
                Time workTime = Time.valueOf(String.format("%02d:%02d:%02d", hours, minutes, secs));
                attendanceEntity.setWorkTime(workTime);

                // 엔티티를 저장 (insert 또는 update 처리)
                AttendanceVO result = attendanceService.attUpdate(attendanceEntity);

                if (result.getWorkTime() == null) {
                    result.setWorkTime(workTime);
                }

                if (result == null) {
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }

                return ResponseEntity.ok(result); // `result`를 반환
            } else {
                // 데이터가 없을 경우 처리
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }



}
