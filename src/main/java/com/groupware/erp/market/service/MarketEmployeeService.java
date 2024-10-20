package com.groupware.erp.market.service;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.market.repository.MarketEmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MarketEmployeeService {
    @Autowired
    private MarketEmployeeRepository marketEmployeeRepository;

    public Optional<EmployeeEntity> findByEmpNo(String empNo) {
        return marketEmployeeRepository.findByEmpNo(empNo);
    }

}
