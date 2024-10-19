package com.groupware.erp.admin.service.impl;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.repository.AdminEmployeeRepository;
import com.groupware.erp.admin.entity.AdminEmployeeEntity;
import com.groupware.erp.admin.service.AdminEmployeeService;
import com.groupware.erp.admin.service.EmployeeUtils;
import com.groupware.erp.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEmployeeServiceImpl implements AdminEmployeeService {

    private static final Logger log = LoggerFactory.getLogger(AdminEmployeeServiceImpl.class);
    private final AdminEmployeeRepository adminEmployeeRepository;
    private final EmployeeUtils employeeUtils;

    @Override
    public String joinEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO){
        String empNo = employeeUtils.generateUniqueEmpNo();
        adminEmployeeDetailDTO.setEmpNo(empNo);
        log.info("부서명 들어갔냐!??!?!? {}", adminEmployeeDetailDTO.getDepartment());
        log.info("얜 멀 갖고잇는거야?? {}", adminEmployeeDetailDTO);

//        String empPassword = passwordEncoder.encode(empNo);
//        adminEmployeeDetailDTO.setEmpPassword(empPassword);

        AdminEmployeeEntity joinEmployee = adminEmployeeDetailDTO.joinEmployee();
        adminEmployeeRepository.save(joinEmployee);

        return empNo;
    }

    @Override
    public List<AdminEmployeeEntity> getEmployees(){
        return adminEmployeeRepository.findAll();
    }

}
