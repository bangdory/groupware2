package com.groupware.erp.admin.employee.service.impl;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import com.groupware.erp.admin.employee.repository.AdminEmployeeRepository;
import com.groupware.erp.admin.employee.service.AdminEmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminEmployeeServiceImpl implements AdminEmployeeService {

    private final AdminEmployeeRepository adminEmployeeRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<AdminEmployeeDetailDTO> findAll() {
        List<AdminEmployeeEntity> employeeEntityList = adminEmployeeRepository.findAll();
        List<AdminEmployeeDetailDTO> employeeDetailDTOList = AdminEmployeeDetailDTO.change(employeeEntityList);
        return employeeDetailDTOList;
    }

    @Override
    public AdminEmployeeDetailDTO findByNo(Long empNo) {
        return AdminEmployeeDetailDTO.toAdminEmployeeDetailDTO(adminEmployeeRepository.findById(empNo).get());
    }

    @Override
    public Long update(AdminEmployeeDetailDTO adminEmployeeDetailDTO) {
        AdminEmployeeEntity adminEmployeeEntity = AdminEmployeeEntity.toUpdateEmployee(adminEmployeeDetailDTO, passwordEncoder);
        return adminEmployeeRepository.save(adminEmployeeEntity).getEmpNo();
    }

    @Override
    public void deleteByNo(Long empNo) {
        adminEmployeeRepository.deleteById(empNo);
    }
}
