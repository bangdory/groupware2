package com.groupware.erp.post.entity;

import com.groupware.erp.employee.entity.EmployeeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name = "community_post")
public class CommunityPostEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_no")
    private int postNo;

    @Column(name = "post_title", nullable = false, length = 100)
    private String postTitle;

    @Column(name = "post_content", nullable = false, length = 2000)
    private String postContent;

    @Column(name = "post_date", nullable = false)
    private LocalDate postDate;

    @Column(name = "post_view")
    private int postView;

    @Column(name = "post_state")
    private Boolean postState;

    @ManyToOne
    @JoinColumn(name = "emp_no", nullable = false)
    private EmployeeEntity employee;
}