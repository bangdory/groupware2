package com.groupware.erp.config;

import com.groupware.erp.config.Interceptor.LoginCheckInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor()) // 로그인 체크 인터셉터
                .order(1) // 인터셉터적용순서
                .addPathPatterns("/**") // 모든 요청에 인터셉터 적용
                .excludePathPatterns("/", "/employee/**"); // 인터셉터 적용을 제외할 요청 목록
    }
}