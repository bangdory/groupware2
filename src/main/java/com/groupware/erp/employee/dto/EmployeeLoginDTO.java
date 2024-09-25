package com.groupware.erp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeLoginDTO {

    private String empEmail;
    private String empPassword;
}