package com.groupware.erp.admin.employee.service;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;

import java.util.List;

public interface AdminEmployeeService {


    List<AdminEmployeeDetailDTO> findAll();
    AdminEmployeeDetailDTO findByNo(Long empNo);
    Long update(AdminEmployeeDetailDTO adminEmployeeDetailDTO);
    void deleteByNo(Long empNo);
}
