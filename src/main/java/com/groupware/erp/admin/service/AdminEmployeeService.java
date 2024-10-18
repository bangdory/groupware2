package com.groupware.erp.admin.service;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface AdminEmployeeService {

    String joinEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO); // 새직원
}
