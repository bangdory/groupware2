package com.groupware.erp.attendance.service;

import com.groupware.erp.attendance.domain.AttendanceEntity;
import com.groupware.erp.attendance.dto.AttendanceVO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceService {

    // 출근 기록을 저장
    AttendanceEntity save(String empNo);

    // 직원 번호로 출근 리스트를 조회
    List<AttendanceEntity> getAttendanceListByEmpNo(String empNo);

    // 빈 데이터 저장
    void EmptyDataSave(AttendanceEntity attendanceEntity);

    // 출퇴근 기록을 업데이트
    AttendanceVO attUpdate(AttendanceEntity entity);

    // 특정 날짜에 대한 출근 기록을 조회
    Optional<AttendanceEntity> findByEmpNoAndRegDate(String empNo, LocalDate regDate);
}
