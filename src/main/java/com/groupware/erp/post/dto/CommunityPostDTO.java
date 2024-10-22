package com.groupware.erp.post.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CommunityPostDTO {

    private int postNo;
    private String postTitle;
    private String postContent;
    private LocalDate postDate;
    private int postView;
    private Boolean postState;
    private String empNo;
    private String empPassword;
    private String empEmail;
    private String empName;
    private String empPhone;
    private Date empHireDate;
    private String department;
    private String empGrade;

}
