package com.groupware.erp.admin.employee.service;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface AdminEmployeeService {

    String joinEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO); // 새직원
}
