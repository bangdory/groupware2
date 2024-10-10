package com.groupware.erp.config;

import com.groupware.erp.employee.entity.EmployeeEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final EmployeeEntity employeeEntity;

    public CustomUserDetails(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    @Override
    public String getUsername() {
        return employeeEntity.getEmpNo(); // 사원번호 (고유식별자)
    }

    @Override
    public String getPassword() {
        return employeeEntity.getEmpPassword(); // 비밀번호
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(employeeEntity.getRole().name())); // 권한
    }

    public String getEmail() {
        return employeeEntity.getEmpEmail(); // email
    }

    public String getDepartment() {
        return employeeEntity.getDepartment(); // 부서
    }

    public String getEmpGrade() {
        return employeeEntity.getEmpGrade(); // 직급
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
