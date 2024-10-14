package com.groupware.erp.login;

import com.groupware.erp.login.annualLeave.AnnualLeaveEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name="employee") // DB테이블이름
public class LoginEntity {
    @Id
    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "emp_password")
    private String empPassword;

    @Column(name = "emp_hiredate")
    private LocalDate empHiredate;

    // 연차 테이블과의 연관관계 설정
    @OneToOne(mappedBy = "loginEntity")  // AnnualLeaveEntity에서 설정한 loginEntity와 연결
    private AnnualLeaveEntity annualLeaveEntity;
}
