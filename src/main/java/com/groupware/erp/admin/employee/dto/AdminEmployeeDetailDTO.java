package com.groupware.erp.admin.employee.dto;

import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import com.groupware.erp.domain.employee.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEmployeeDetailDTO {

    private Long empNo;
    private String empPassword;
    private String empEmail;
    private String empName;
    private String empPhone;
    private LocalDate empHireDate;
    private String department;
    private String empGrade;
    private Role role;

    public static AdminEmployeeDetailDTO toAdminEmployeeDetailDTO(AdminEmployeeEntity adminEmployeeEntity) {
        AdminEmployeeDetailDTO employeeDetailDTO = new AdminEmployeeDetailDTO();
        employeeDetailDTO.setEmpNo(adminEmployeeEntity.getEmpNo());
        employeeDetailDTO.setEmpEmail(adminEmployeeEntity.getEmpEmail());
        employeeDetailDTO.setEmpPassword(adminEmployeeEntity.getEmpPassword());
        employeeDetailDTO.setEmpName(adminEmployeeEntity.getEmpName());
        return employeeDetailDTO;
    }

    public static List<AdminEmployeeDetailDTO> change(List<AdminEmployeeEntity> employeeEntityList) {
        List<AdminEmployeeDetailDTO> employeeDetailDTOList = new ArrayList<>();
        for (AdminEmployeeEntity adminEmployeeEntity : employeeEntityList) {
            employeeDetailDTOList.add(toAdminEmployeeDetailDTO(adminEmployeeEntity));
        }
        return employeeDetailDTOList;
    }
}
