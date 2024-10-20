package com.groupware.erp.employee.service.impl;

import com.groupware.erp.employee.dto.EmployeeMapperDTO;
import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.repository.EmployeeMapperRepository;
import com.groupware.erp.employee.repository.EmployeeRepository;
import com.groupware.erp.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // Repository 생성자 주입
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapperRepository employeeMapperRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원목록 조회
    @Override
    public List<EmployeeMapperDTO> employeeList(EmployeeMapperDTO employeeMapperDTO) {
        return  employeeMapperRepository.emplyeeList(employeeMapperDTO);
    }

    @Override
    public Optional<EmployeeEntity> findByEmpNo(String empNo) {
        return employeeRepository.findByEmpNo(empNo);
    }

}
