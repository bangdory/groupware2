package com.groupware.erp.admin.service.impl;

import com.groupware.erp.admin.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.repository.AdminEmployeeRepository;
import com.groupware.erp.admin.entity.AdminEmployeeEntity;
import com.groupware.erp.admin.service.AdminEmployeeService;
import com.groupware.erp.admin.service.EmployeeUtils;
import com.groupware.erp.employee.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
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

    @Override
    public void updateEmployeeStatus(String empNo, String status){
        log.info("서비스는 실행대엇다!!!!!");
        log.info("empNo: {}", empNo);
        log.info("status: {}", status);
        adminEmployeeRepository.updateEmployeeStatus(empNo, status);
    }

    @Override
    public void updateEmployee(AdminEmployeeEntity adminEmployeeEntity){
        log.info("updateEmployee 실행댐 {}", adminEmployeeEntity);
        adminEmployeeRepository.save(adminEmployeeEntity);
    }

}
