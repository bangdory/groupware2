package com.groupware.erp.login.annualLeave;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnnualLeaveRepository extends JpaRepository<AnnualLeaveEntity, Integer> {

    Optional<AnnualLeaveEntity> findByEmpNo(String empNo);

}
