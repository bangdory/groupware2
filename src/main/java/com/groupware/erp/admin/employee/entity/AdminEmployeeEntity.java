package com.groupware.erp.admin.employee.entity;

import com.groupware.erp.admin.employee.dto.AdminEmployeeDetailDTO;
import com.groupware.erp.domain.employee.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name="employee")	// DB table 이름
public class AdminEmployeeEntity {

    @Id // PK컬럼
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment와 같은 역할
    @Column(name = "emp_no") // DB 컬럼 이름
    private Long empNo;

    @Column(name = "emp_email", length = 50, unique = true)  // varchar(50)에 unipue 속성
    private String empEmail;

    @Column(name = "emp_password")
    private String empPassword;

    @Column(name = "emp_name") // 아무 지정을 안해주면 varchar(255)
    private String empName;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public static AdminEmployeeEntity toUpdateEmployee(AdminEmployeeDetailDTO adminEmployeeDetailDTO, PasswordEncoder passwordEncoder) {
        AdminEmployeeEntity adminEmployeeEntity = new AdminEmployeeEntity();

        adminEmployeeEntity.setEmpEmail(adminEmployeeDetailDTO.getEmpEmail());
        adminEmployeeEntity.setEmpPassword(passwordEncoder.encode(adminEmployeeDetailDTO.getEmpPassword()));
        adminEmployeeEntity.setEmpName(adminEmployeeDetailDTO.getEmpName());

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return adminEmployeeEntity;
    }
}
