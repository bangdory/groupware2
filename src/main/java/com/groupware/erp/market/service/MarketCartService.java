package com.groupware.erp.market.service;


import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.repository.EmployeeRepository;
import com.groupware.erp.market.model.MarketCartEntity;

import com.groupware.erp.market.repository.MarketCartRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public MarketCartEntity addToCart(String empNo, int productNo, int productAmount, String name, int price) {
        // 1. 현재 카트에서 해당 productNo를 가진 아이템을 조회
        MarketCartEntity existingCartItem = marketCartRepository.findByEmpNoAndProductNo(empNo, productNo);

        if (existingCartItem != null) {
            // 2. 아이템이 존재하면 수량 증가
            existingCartItem.setProductAmount(existingCartItem.getProductAmount() + productAmount);
            return marketCartRepository.save(existingCartItem); // 업데이트된 아이템 저장
        } else {
            // 3. 아이템이 존재하지 않으면 새 아이템 생성
            MarketCartEntity cart = new MarketCartEntity();
            cart.setEmpNo(empNo);
            cart.setProductNo(productNo);
            cart.setProductAmount(productAmount);
            cart.setName(name);
            cart.setPrice(price);
            return marketCartRepository.save(cart); // 새 아이템 저장
        }
    }

    //카트 삭제

    public void deleteCartItem(String empNo, int productNo) {
        // 제품 번호와 사원 번호로 항목을 검색
        MarketCartEntity cartItem = marketCartRepository.findByEmpNoAndProductNo(empNo, productNo);

        if (cartItem == null) {
            throw new EntityNotFoundException("Cart item not found");
        }

        marketCartRepository.delete(cartItem); // 항목 삭제
    }

}

