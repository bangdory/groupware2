package com.groupware.erp.login;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

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
}
