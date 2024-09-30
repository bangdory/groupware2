package com.groupware.erp.employee.service;

import com.groupware.erp.employee.dto.EmployeeJoinDTO;
import com.groupware.erp.employee.dto.EmployeeMapperDTO;

import java.util.List;

public interface EmployeeService {

    // 회원가입 처리
//    Long join(EmployeeJoinDTO employeeJoinDTO);

    // 회원목록 조회
    List<EmployeeMapperDTO> employeeList(EmployeeMapperDTO employeeMapperDTO);
}
