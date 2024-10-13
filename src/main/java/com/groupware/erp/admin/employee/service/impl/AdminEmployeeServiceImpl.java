package com.groupware.erp.admin.employee.service.impl;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import com.groupware.erp.admin.employee.repository.AdminEmployeeRepository;
import com.groupware.erp.admin.employee.service.AdminEmployeeService;
import com.groupware.erp.admin.employee.service.EmployeeUtils;
import com.groupware.erp.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminEmployeeServiceImpl implements AdminEmployeeService {

    private final AdminEmployeeRepository adminEmployeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeUtils employeeUtils;

    @Override
    public String joinEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO){
        String empNo = employeeUtils.generateUniqueEmpNo();
        adminEmployeeDetailDTO.setEmpNo(empNo);

//        String empPassword = passwordEncoder.encode(empNo);
        adminEmployeeDetailDTO.setEmpPassword(empNo);

        AdminEmployeeEntity joinEmployee = adminEmployeeDetailDTO.joinEmployee(passwordEncoder);
        adminEmployeeRepository.save(joinEmployee);

        return empNo;
    }

}
