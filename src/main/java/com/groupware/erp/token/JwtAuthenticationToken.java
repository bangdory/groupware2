package com.groupware.erp.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final String username;
    private final String empEmail;
    private final String department;
    private final String empGrade;
    private final String token;
    private final Collection<GrantedAuthority> authorities;

    public JwtAuthenticationToken(String username,
                                  String empEmail,
                                  String department,
                                  String empGrade,
                                  String token) {
        super(null);
        this.username = username;
        this.empEmail = empEmail;
        this.department = department;
        this.empGrade = empGrade;
        this.token = token;
        this.authorities = null;
        setAuthenticated(false);
    }

    public JwtAuthenticationToken(String username,
                                  String empEmail,
                                  String department,
                                  String empGrade,
                                  String token,
                                  Collection<GrantedAuthority> authorities) {
        super(authorities);
        this.username = username;
        this.empEmail = empEmail;
        this.department = department;
        this.empGrade = empGrade;
        this.token = token;
        this.authorities = authorities;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token; // 토큰 자체가 자격증명(credentials)
    }

    @Override
    public Object getPrincipal() {
        return username; // 사용자의 아이디(username)
    }

    // 추가 정보 반환
    public String getEmail() {
        return empEmail;
    }

    public String getDepartment() {
        return department;
    }

    public String getGrade() {
        return empGrade;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

}
