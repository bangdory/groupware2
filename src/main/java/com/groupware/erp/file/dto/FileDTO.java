package com.groupware.erp.file.dto;

import jakarta.persistence.Column;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class FileDTO {

    private int fileNo;
    private String originalFilename;
    private String fileName;
    private String fileSize;
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
    private int postNo;
    private String postTitle;
    private String postContent;
    private LocalDate postDate;
    private int postView;
    private Boolean postState;

}
