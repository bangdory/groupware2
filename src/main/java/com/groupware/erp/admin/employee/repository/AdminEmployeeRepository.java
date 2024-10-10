package com.groupware.erp.admin.employee.repository;

import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminEmployeeRepository extends JpaRepository<AdminEmployeeEntity, String> {

    Optional<AdminEmployeeEntity> findByEmpNo(String empNo);

    void deleteByEmpNo(String empNo);
}
