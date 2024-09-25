package com.groupware.erp.employee.repository;

import com.groupware.erp.employee.dto.EmployeeMapperDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmployeeMapperRepository {

    List<EmployeeMapperDTO> emplyeeList(EmployeeMapperDTO employeeMapperDTO);
}