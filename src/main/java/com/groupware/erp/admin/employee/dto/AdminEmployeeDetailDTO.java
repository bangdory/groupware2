package com.groupware.erp.admin.employee.dto;

import com.groupware.erp.admin.employee.entity.AdminEmployeeEntity;
import com.groupware.erp.domain.employee.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminEmployeeDetailDTO {

    private String empNo;
    private String empPassword;
    private String empEmail;
    private String empName;
    private String empPhone;
    private LocalDate empHireDate;
    private String department;
    private String empGrade;
    private Role role;

    public AdminEmployeeEntity joinEmployee(PasswordEncoder passwordEncoder) {
        AdminEmployeeEntity employee = new AdminEmployeeEntity();
        employee.setEmpNo(this.empNo);
        employee.setEmpPassword(passwordEncoder.encode(this.empPassword));
        employee.setEmpEmail(this.empEmail);
        employee.setEmpName(this.empName);
        employee.setEmpPhone(this.empPhone);
        employee.setEmpHireDate(this.empHireDate);
        employee.setDepartment(this.department);
        employee.setEmpGrade(this.empGrade);
        employee.setRole(this.role != null ? this.role : Role.USER);

        return employee;
    }

}
