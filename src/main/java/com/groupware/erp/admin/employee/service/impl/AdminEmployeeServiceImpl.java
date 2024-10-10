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
    public String newEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO){
        AdminEmployeeEntity newEmployee = AdminEmployeeEntity.newEmployee(adminEmployeeDetailDTO);
        return adminEmployeeRepository.save(newEmployee).getEmpNo(); // empNo 반환
    }

    @Override
    public List<AdminEmployeeDetailDTO> findAll() {
        List<AdminEmployeeEntity> employeeEntityList = adminEmployeeRepository.findAll();
        List<AdminEmployeeDetailDTO> employeeDetailDTOList = AdminEmployeeDetailDTO.change(employeeEntityList);
        return employeeDetailDTOList;
    }

    @Override
    public Optional<AdminEmployeeEntity> findByEmpNo(String empNo) {
        return adminEmployeeRepository.findByEmpNo(empNo);
    }

    @Override
    public String update(AdminEmployeeDetailDTO adminEmployeeDetailDTO) {
        AdminEmployeeEntity adminEmployeeEntity = AdminEmployeeEntity.toUpdateEmployee(adminEmployeeDetailDTO, passwordEncoder);

        return adminEmployeeRepository.save(adminEmployeeEntity).getEmpNo();
    }

    @Override
    public void deleteByEmpNo(String empNo) {
        adminEmployeeRepository.deleteById(empNo);
    }
}
