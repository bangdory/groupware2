package com.groupware.erp.vacation.service;

import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;
import com.groupware.erp.vacation.repository.VacationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VacationServiceImpl implements VacationService{

    private final VacationRepository vacationRepository;

    // 생성자 주입
    public VacationServiceImpl(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public VacationEntity save(VacationDTO dto) {
        // VacationDTO -> VacationEntity로 변환
        VacationEntity entity = VacationEntity.builder()
                .empNo(dto.getEmpNo())
                .reqDate(dto.getReqDate())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .requestReason(dto.getRequestReason())
                .totalDays(dto.getTotalDays())
                .build();

        // 변환된 엔티티를 저장
        return vacationRepository.save(entity);
    }

    @Override
    public List<VacationEntity> findByEmpNoOrderByVacNoDesc(String empNo) {
        return vacationRepository.findByEmpNoOrderByVacNoDesc(empNo);
    }


}
