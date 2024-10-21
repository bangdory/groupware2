package com.groupware.erp.employee.entity;

import com.groupware.erp.domain.employee.Role;
import com.groupware.erp.login.annualLeave.AnnualLeaveEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name="employee")	// DB table 이름
public class EmployeeEntity {

    @Id // PK컬럼
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment와 같은 역할, 사원번호 개별부여 할 거라서 주석.
    @Column(name = "emp_no") // DB 컬럼 이름
    private String empNo;

    @Column(name = "emp_password")
    private String empPassword;

    @Column(name = "emp_email", unique = true)  // varchar(50)에 unique 속성
    private String empEmail;

    @Column(name = "emp_name") // 사원이름
    private String empName;

    @Column(name = "emp_phone") // 사원연락처
    private String empPhone;

    @Column(name = "emp_hiredate") // 입사일
    private LocalDate empHireDate;

    @Column(name = "department") // 부서
    private String department;

    @Column(name = "emp_grade") // 직급
    private String empGrade;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false) // 그룹웨어 권한 (관리자여부)
    private Role role;

    // annualleave 테이블과 1:1 매핑 설정 (외래 키 관계)
    @OneToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no") // 외래 키 관계 설정
    private AnnualLeaveEntity annualLeave;

//    // MemberJoinDTO -> MemberEntity 객체로 변환
//    public static EmployeeEntity joinEmployee(EmployeeJoinDTO employeeJoinDTO, PasswordEncoder passwordEncoder) {
//        EmployeeEntity employeeEntity = new EmployeeEntity();
//
//        employeeEntity.setEmpNo(employeeJoinDTO.getEmpNo());
//        employeeEntity.setEmpPassword(passwordEncoder.encode(employeeJoinDTO.getEmpPassword()));
//        employeeEntity.setEmpEmail(employeeJoinDTO.getEmpEmail());
//        employeeEntity.setEmpName(employeeJoinDTO.getEmpName());
//        employeeEntity.setEmpPhone(employeeJoinDTO.getEmpPhone());
//        employeeEntity.setEmpHireDate(employeeJoinDTO.getEmpHireDate());
//        employeeEntity.setDepartment(employeeJoinDTO.getDepartment());
//        employeeEntity.setEmpGrade(employeeJoinDTO.getEmpGrade());
//        employeeEntity.setRole(employeeJoinDTO.getRole());
//        // 변환이 완료된 memberEntity 객체를 넘겨줌
//        return employeeEntity;
//    }
}