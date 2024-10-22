package com.groupware.erp.notice.entity;

import com.groupware.erp.employee.entity.EmployeeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name = "community_notice")
public class CommunityNoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noticeNo;

    @Column(nullable = false)
    private String noticeTitle;

    @Column(nullable = false, length = 2000)
    private String noticeContent;

    @Column(nullable = false)
    private LocalDate noticeDate = LocalDate.now();

    @Column
    private int noticeView;

    @Column
    private Boolean noticeState;

    @ManyToOne
    @JoinColumn(name = "emp_no", nullable = false)
    private EmployeeEntity employee;

}
