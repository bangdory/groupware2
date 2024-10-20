package com.groupware.erp.vacation.service;

import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;

import java.util.List;

public interface VacationService {

    VacationEntity save(VacationDTO dto);

    List<VacationEntity> findByEmpNoOrderByVacNoDesc(String empNo);

}
