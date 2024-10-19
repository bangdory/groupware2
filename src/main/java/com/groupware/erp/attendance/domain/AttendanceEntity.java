package com.groupware.erp.attendance.domain;

import com.groupware.erp.login.LoginEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "attendance")
public class AttendanceEntity {

    @Id
    @Column(name = "att_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto Increment 설정
    private Long attNo;

    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "reg_date")
    private LocalDate regDate;

    @Column(name = "arr_time")
    private Time arrTime;

    @Column(name = "lev_time")
    private Time levTime;

    @Column(name = "att_stat")
    private String attStat;

    @Column(name = "work_time")
    private Time workTime;

    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no", insertable = false, updatable = false)
    private LoginEntity loginEntity;
}
