package com.groupware.erp.attendance.dto;

import lombok.Data;

import java.sql.Time;
import java.time.LocalDate;

@Data
public class AttendanceVO {

    private Long attNo;        // 출근 기록 번호
    private String empNo;      // 사원 번호
    private LocalDate regDate; // 출근 날짜
    private Time arrTime;      // 출근 시간
    private Time levTime;      // 퇴근 시간
    private String attStat;    // 출근 상태 (출근/퇴근/휴가)
    private Time workTime;     // 근무 시간
}
