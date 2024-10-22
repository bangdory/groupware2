package com.groupware.erp.comment.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.post.entity.CommunityPostEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "community_comment")
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "commentNo")
public class CommunityCommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no")
    private int commentNo;

    @ManyToOne
    @JoinColumn(name = "post_no", nullable = false)
    private CommunityPostEntity post;

    @ManyToOne
    @JoinColumn(name = "emp_no")
    private EmployeeEntity employee;

    @Column(name = "comment_content", nullable = false, length = 500)
    private String commentContent;

    @Column(name = "comment_date", nullable = false)
    private LocalDate commentDate;

    @Column(name = "comment_parent", nullable = false)
    private int commentParent;

    @Column(name = "comment_step")
    private int commentStep;

    @Column(name = "comment_ref")
    private int commentRef;

    @Column(name = "comment_reforder")
    private int commentRefOrder;

    @Column(name = "comment_answernum")
    private int commentAnswerNum;

    @Column(name = "comment_state")
    private Boolean commentState;


}