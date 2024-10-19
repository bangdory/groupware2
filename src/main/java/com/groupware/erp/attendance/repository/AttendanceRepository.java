package com.groupware.erp.attendance.repository;

import com.groupware.erp.attendance.domain.AttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<AttendanceEntity, Long> {

    // 직원 번호(empNo)와 등록 날짜(regDate)로 출근 기록 조회
    Optional<AttendanceEntity> findByEmpNoAndRegDate(String empNo, LocalDate regDate);

    // 직원 번호(empNo)로 모든 출근 기록 조회, 등록 날짜 역순으로 정렬
    List<AttendanceEntity> findByEmpNoOrderByRegDateDesc(String empNo);
}
