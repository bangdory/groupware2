package com.groupware.erp.config.Interceptor;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.util.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // preHandle는 request가 controller에 가기 전에 작업 수행

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        EmployeeEntity employeeEntity = (EmployeeEntity)principal;

        if (StringUtil.isEmpty(employeeEntity.getEmpName())) {
            // 미로그인 상태면 로그인 화면으로 보냄
            response.sendRedirect("/employee/login");
            return false;
        } else {
            // 로그인 정상 상태
            return true;
        }
    }
}
