package com.groupware.erp.employee.service;

import com.groupware.erp.employee.dto.EmployeeMapperDTO;
import com.groupware.erp.employee.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    // 회원목록 조회
    List<EmployeeMapperDTO> employeeList(EmployeeMapperDTO employeeMapperDTO);

    Optional<EmployeeEntity> findByEmpNo (String empNo);

}
