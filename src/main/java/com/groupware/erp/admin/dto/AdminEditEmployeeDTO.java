package com.groupware.erp.admin.dto;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class AdminEditEmployeeDTO {

    private String empNo;
    private Date regDate;
    private Time arrTime;
    private Time levTime;
    private Date reqDate;
    private Date startDate;
    private Date endDate;
    private boolean approveBoolean;

}
