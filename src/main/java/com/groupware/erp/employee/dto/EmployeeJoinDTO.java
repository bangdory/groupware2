package com.groupware.erp.employee.dto;

import com.groupware.erp.domain.employee.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeJoinDTO {

    private Long empNo;
    private String empPassword;
    private String empEmail;
    private String empName;
    private String empPhone;
    private LocalDate empHireDate;
    private String department;
    private String empGrade;
    private Role role;
}