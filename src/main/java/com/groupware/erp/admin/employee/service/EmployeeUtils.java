package com.groupware.erp.admin.employee.service;

import com.groupware.erp.employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class EmployeeUtils {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeUtils(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public static String generateEmpNo() {
        // 현재날짜 yyyyMMdd 형식으로 포맷저장
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        // UUID 2글자 가져오기
        String uuidPart = UUID.randomUUID().toString().replace("-", "").substring(0, 2);

        // 날짜 + UUID (String 타입)
        String empNo = uuidPart + datePart;

        // empNo 리턴
        return empNo;
    }


    public String generateUniqueEmpNo() {
        String empNo;
        do {
            empNo = generateEmpNo();
        } while (employeeRepository.findByEmpNo(empNo).isPresent()); // 중복체크

        return empNo;
    }

}
