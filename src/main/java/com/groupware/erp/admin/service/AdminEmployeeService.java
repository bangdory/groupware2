package com.groupware.erp.admin.service;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.entity.AdminEmployeeEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface AdminEmployeeService {

    String joinEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO); // 새직원

    List<AdminEmployeeEntity> getEmployees();

    void updateEmployeeStatus(String empNo, String status);

    void updateEmployee(AdminEmployeeEntity adminEmployeeEntity);
}
