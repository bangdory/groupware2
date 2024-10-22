package com.groupware.erp.vacation.service;

import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;
import com.groupware.erp.vacation.repository.VacationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VacationServiceImpl implements VacationService{

    private final VacationRepository vacationRepository;

    // 생성자 주입
    public VacationServiceImpl(VacationRepository vacationRepository) {
        this.vacationRepository = vacationRepository;
    }

    public VacationEntity findByVacNo(int vacNo) {
        return vacationRepository.findByVacNo(vacNo);
    }

    @Override
    public int updateVacationApprove(long vacNo, int approveBoolean) {
        return vacationRepository.updateVacationApprove(vacNo, approveBoolean);
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

    @Override
    public Optional<VacationEntity> findLatestByEmpNo(String empNo) {
        return vacationRepository.findLatestByEmpNo(empNo);
    }


}
