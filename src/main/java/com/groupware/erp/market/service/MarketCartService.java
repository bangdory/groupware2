package com.groupware.erp.market.service;


import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.repository.EmployeeRepository;
import com.groupware.erp.market.model.MarketCartEntity;

import com.groupware.erp.market.repository.MarketCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketCartService {

    @Autowired
    private MarketCartRepository marketCartRepository;

    @Autowired
    private EmployeeRepository employeeRepository;


    //카트 불러오기
    public List<MarketCartEntity> getCart(String empNo) {
        return marketCartRepository.findByEmpNo(empNo);
    }


    //카트에 등록
    public MarketCartEntity addToCart(String empNo, int productNo, int productAmount) {


        MarketCartEntity cart = new MarketCartEntity();
        cart.setEmpNo(empNo);
        cart.setProductNo(productNo);
        cart.setProductAmount(productAmount);

        return marketCartRepository.save(cart);
    }
    //카트 삭제
    public void deleteCartItem(String empNo, int productNo) {
        marketCartRepository.deleteByEmpNoAndProductNo(empNo, productNo);
    }

}

