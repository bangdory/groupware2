package com.groupware.erp.vacation.service;

import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;

import java.util.List;
import java.util.Optional;

public interface VacationService {

    VacationEntity save(VacationDTO dto);

    List<VacationEntity> findByEmpNoOrderByVacNoDesc(String empNo);

    Optional<VacationEntity> findLatestByEmpNo(String empNo);

    VacationEntity findByVacNo(int vacNo);

    int updateVacationApprove(long vacNo, int approveBoolean);

}
