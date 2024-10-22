package com.groupware.erp.vacation.repository;

import com.groupware.erp.vacation.domain.VacationEntity;
import com.groupware.erp.vacation.dto.VacationDTO;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacationRepository extends JpaRepository<VacationEntity, Long> {

    List<VacationEntity> findByEmpNoOrderByVacNoDesc(String empNo);

    VacationEntity save(VacationDTO dto);

    @Query("SELECT v FROM VacationEntity v WHERE v.empNo = :empNo ORDER BY v.reqDate DESC LIMIT 1")
    Optional<VacationEntity> findLatestByEmpNo(@Param("empNo") String empNo);

    VacationEntity findByVacNo(int vacNo);

    @Modifying
    @Transactional
    @Query("UPDATE VacationEntity v SET v.approveBoolean = :approveBoolean WHERE v.vacNo = :vacNo")
    int updateVacationApprove(@Param("vacNo") Long vacNo, @Param("approveBoolean") int approveBoolean);

}
