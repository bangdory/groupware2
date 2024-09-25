package com.groupware.erp.admin.employee.repository;

import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminEmployeeRepository extends JpaRepository<AdminEmployeeEntity, Long> {
}
