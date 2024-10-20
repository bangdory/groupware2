package com.groupware.erp.market.repository;

import com.groupware.erp.market.model.MarketDiscountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketDiscountRepository extends JpaRepository<MarketDiscountEntity, Integer> {
    // 제품 번호에 따른 할인 정보 조회
    MarketDiscountEntity findByProductNo(int productNo);
}