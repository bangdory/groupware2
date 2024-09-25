package com.groupware.erp.employee.repository;

import com.groupware.erp.employee.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByEmpEmail(String empEmail);
}
