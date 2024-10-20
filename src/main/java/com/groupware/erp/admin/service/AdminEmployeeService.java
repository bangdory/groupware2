package com.groupware.erp.admin.service;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.entity.AdminEmployeeEntity;

import java.util.List;

public interface AdminEmployeeService {

    String joinEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO); // 새직원

    public List<AdminEmployeeEntity> getEmployees();
}
