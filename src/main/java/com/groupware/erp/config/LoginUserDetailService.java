package com.groupware.erp.config;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginUserDetailService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    // 로그인 처리
    @Override
    public UserDetails loadUserByUsername(String empEmail) throws UsernameNotFoundException {
        EmployeeEntity employeeEntity = employeeRepository.findByEmpEmail(empEmail).orElseThrow(() -> new UsernameNotFoundException("없는 회원 입니다..."));

        return User.builder().username(employeeEntity.getEmpName())
                   .password(employeeEntity.getEmpPassword())
                   .roles(employeeEntity.getRole().name())
                   .build();
    }
}
