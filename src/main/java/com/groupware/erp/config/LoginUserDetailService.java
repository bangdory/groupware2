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
    public UserDetails loadUserByUsername(String empNo) throws UsernameNotFoundException {

        // empNo로 회원정보 검증

        EmployeeEntity employeeEntity = employeeRepository.findByEmpNo(empNo)
                .orElseThrow(() -> new UsernameNotFoundException("없는 회원 입니다..."));

        return new CustomUserDetails(employeeEntity); // CustomerUserDetails 사용

    }
}
