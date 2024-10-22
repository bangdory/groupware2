package com.groupware.erp.notice.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CommunityNoticeDTO {
    private int noticeNo;
    private String noticeTitle;
    private String noticeContent;
    private LocalDate noticeDate;
    private int noticeView;
    private Boolean noticeState;
    private String empNo;
    private String empPassword;
    private String empEmail;
    private String empName;
    private String empPhone;
    private Date empHireDate;
    private String department;
    private String empGrade;
}