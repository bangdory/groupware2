package com.groupware.erp.attendance.config;

import com.groupware.erp.attendance.service.AttendanceService;
import com.groupware.erp.attendance.service.AttendanceServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class AttendanceConfig {

    @Bean
    public AttendanceService attendanceService() {
        return new AttendanceServiceImpl();
    }

}
