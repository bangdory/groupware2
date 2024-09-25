package com.groupware.erp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeMapperDTO {

    private Long empNo;
    private String empEmail;
    private String empPassword;
    private String empName;

    public EmployeeMapperDTO(String empEmail, String empPassword, String empName) {
        this.empEmail = empEmail;
        this.empPassword = empPassword;
        this.empName = empName;
    }
}