package com.groupware.erp.admin.employee.entity;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.domain.employee.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name="employee")	// DB table 이름
public class AdminEmployeeEntity {

    @Id // PK컬럼
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment와 같은 역할
    @Column(name = "emp_no") // DB 컬럼 이름
    private Long empNo;

    @Column(name = "emp_password")
    private String empPassword;

    @Column(name = "emp_email", length = 50, unique = true)  // varchar(50)에 unique 속성
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

    public static AdminEmployeeEntity hireEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO, PasswordEncoder passwordEncoder) {
        AdminEmployeeEntity adminEmployeeEntity = new AdminEmployeeEntity();

        adminEmployeeEntity.setEmpNo(adminEmployeeDetailDTO.getEmpNo());
        adminEmployeeEntity.setEmpPassword(adminEmployeeDetailDTO.getEmpPassword());
        adminEmployeeEntity.setEmpEmail(adminEmployeeDetailDTO.getEmpEmail());
        adminEmployeeEntity.setEmpName(adminEmployeeDetailDTO.getEmpName());
        adminEmployeeEntity.setEmpPhone(adminEmployeeDetailDTO.getEmpPhone());
        adminEmployeeEntity.setEmpHireDate(adminEmployeeDetailDTO.getEmpHireDate());
        adminEmployeeEntity.setDepartment(adminEmployeeDetailDTO.getDepartment());
        adminEmployeeEntity.setEmpGrade(adminEmployeeDetailDTO.getEmpGrade());
        adminEmployeeEntity.setRole(Role.USER);

        return adminEmployeeEntity;
    }

    public static AdminEmployeeEntity toUpdateEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO, PasswordEncoder passwordEncoder) {
        AdminEmployeeEntity adminEmployeeEntity = new AdminEmployeeEntity();

        adminEmployeeEntity.setEmpNo(adminEmployeeEntity.getEmpNo());
        adminEmployeeEntity.setEmpEmail(adminEmployeeDetailDTO.getEmpEmail());
        adminEmployeeEntity.setEmpPassword(passwordEncoder.encode(adminEmployeeDetailDTO.getEmpPassword()));
        adminEmployeeEntity.setEmpName(adminEmployeeDetailDTO.getEmpName());
        adminEmployeeEntity.setRole(adminEmployeeDetailDTO.getRole()); // 최초 회원가입 시 user(일반사용자)권한 부여.

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return adminEmployeeEntity;
    }
}
