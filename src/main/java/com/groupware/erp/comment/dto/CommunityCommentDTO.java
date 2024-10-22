package com.groupware.erp.comment.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class CommunityCommentDTO {

    private int commentNo;
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
    private String commentContent;
    private LocalDate commentDate;
    private int commentParent;
    private int commentStep;
    private int commentRef;
    private int commentRefOrder;
    private int commentAnswerNum;
    private Boolean commentState;
}
