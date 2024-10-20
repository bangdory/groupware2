package com.groupware.erp.vacation.repository;

import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<VacationEntity, Long> {

    List<VacationEntity> findByEmpNoOrderByVacNoDesc(String empNo);

    VacationEntity save(VacationDTO dto);

}
