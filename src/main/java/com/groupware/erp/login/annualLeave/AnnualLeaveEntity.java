package com.groupware.erp.login.annualLeave;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.login.LoginEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "annualleave")
public class AnnualLeaveEntity {

    @Id
    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "emp_hiredate")
    private LocalDate empHiredate;

    @Column(name = "total_ann")
    private int totalAnn;

    @Column(name = "use_ann")
    private int useAnn;

    @Column(name = "rem_ann")
    private int remAnn;

    @Column(name = "pending_ann")
    private int pendingAnn;

    @OneToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    private LoginEntity loginEntity; // 연관 관계 설정

    @OneToOne(mappedBy = "annualLeave")
    private EmployeeEntity employee;
}

