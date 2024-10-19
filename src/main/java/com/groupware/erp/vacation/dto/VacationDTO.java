package com.groupware.erp.vacation.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VacationDTO {

    private String empNo;
    private LocalDate reqDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private String requestReason;
    private int totalDays;

}
