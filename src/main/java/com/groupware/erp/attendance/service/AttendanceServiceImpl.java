package com.groupware.erp.attendance.service;

import com.groupware.erp.attendance.domain.AttendanceEntity;
import com.groupware.erp.attendance.dto.AttendanceVO;
import com.groupware.erp.attendance.repository.AttendanceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private static final Logger log = LoggerFactory.getLogger(AttendanceServiceImpl.class);
    private final AttendanceRepository attendanceRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public AttendanceEntity save(String empNo) {
        try {
            LocalDate today = LocalDate.now();

            // empNo와 regDate로 기존 데이터를 조회
            Optional<AttendanceEntity> existingRecord = attendanceRepository.findByEmpNoAndRegDate(empNo, today);


            // 이미 데이터가 있는 경우 출근 처리를 하지 않음
            if (existingRecord.isPresent()) {
                log.info("이미 출근 기록이 존재합니다.");
                return null;  // 중복 출근이므로 저장하지 않음
            }


            AttendanceEntity entity = new AttendanceEntity();

            entity.setEmpNo(empNo);
            entity.setArrTime(Time.valueOf(LocalTime.now().withNano(0)));
            entity.setRegDate(today);
            entity.setAttStat("출근");

            return attendanceRepository.save(entity); // 저장된 엔티티를 반환


        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<AttendanceEntity> getAttendanceListByEmpNo(String empNo) {
        return attendanceRepository.findByEmpNoOrderByRegDateDesc(empNo);
    }

    @Override
    public void EmptyDataSave (AttendanceEntity attendanceEntity) {
        attendanceRepository.save(attendanceEntity);
    }

    @Override
    public AttendanceVO attUpdate (AttendanceEntity entity) {
        // 엔티티 저장
        AttendanceEntity attEntity = attendanceRepository.save(entity);

        // 저장된 엔티티를 DTO로 변환
        AttendanceVO vo = new AttendanceVO();
        vo.setEmpNo(attEntity.getEmpNo());
        vo.setRegDate(attEntity.getRegDate());
        vo.setArrTime(attEntity.getArrTime());
        vo.setLevTime(attEntity.getLevTime());
        vo.setAttStat(attEntity.getAttStat());

        return vo;
    }

    @Override
    public Optional<AttendanceEntity> findByEmpNoAndRegDate(String empNo, LocalDate regDate) {
        return attendanceRepository.findByEmpNoAndRegDate(empNo, regDate);
    }

}
