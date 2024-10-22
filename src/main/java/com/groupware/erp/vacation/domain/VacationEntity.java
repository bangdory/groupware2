package com.groupware.erp.vacation.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "vacation")
public class VacationEntity {

    @Id
    @Column(name = "vac_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 또는 GenerationType.AUTO
    private long vacNo;

    @Column(name = "emp_no")
    private String empNo;

    @Column(name = "req_date")
    private LocalDate reqDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Column(name = "approve_boolean")
    private int approveBoolean;

    @Column(name = "approve_date")
    private LocalDate approveDate;

    @Column(name = "request_reason")
    private String requestReason;

    @Column(name = "rejection_reason")
    private String rejectionReason;

    @Column(name = "authorizer")
    private int authorizer;

    @Column(name = "total_days")
    private int totalDays;

    // 빌더 패턴 사용 (선택 사항)
    @Builder
    public VacationEntity(long vacNo, String empNo, LocalDate reqDate, LocalDate startDate, LocalDate endDate, String requestReason, int totalDays, int approveBoolean) {
        this.vacNo = vacNo;
        this.empNo = empNo;
        this.reqDate = reqDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.requestReason = requestReason;
        this.totalDays = totalDays;
        this.approveBoolean = approveBoolean;
    }

    @Override
    public String toString() {
        return "VacationEntity{" +
                "vacNo=" + vacNo +
                ", empNo='" + empNo + '\'' +
                ", reqDate=" + reqDate +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", approveBoolean=" + approveBoolean +
                '}';
    }

}
