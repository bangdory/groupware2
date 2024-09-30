package com.groupware.erp.employee.service.impl;

import com.groupware.erp.employee.dto.EmployeeJoinDTO;
import com.groupware.erp.employee.dto.EmployeeMapperDTO;
import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.repository.EmployeeMapperRepository;
import com.groupware.erp.employee.repository.EmployeeRepository;
import com.groupware.erp.employee.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    // Repository 생성자 주입
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapperRepository employeeMapperRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 처리
//    @Override
//    public Long join(EmployeeJoinDTO employeeJoinDTO) {
//        // JPA Repository는 무조건 Entity 타입만 받기 때문에 Entity 타입으로 변경
//        EmployeeEntity employeeEntity = EmployeeEntity.joinEmployee(employeeJoinDTO, passwordEncoder);
//        return employeeRepository.save(employeeEntity).getEmpNo();
//    }

    // 회원목록 조회
    @Override
    public List<EmployeeMapperDTO> employeeList(EmployeeMapperDTO employeeMapperDTO) {
        return  employeeMapperRepository.emplyeeList(employeeMapperDTO);
    }

}
