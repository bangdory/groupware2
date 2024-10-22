package com.groupware.erp.market.repository;

import com.groupware.erp.market.model.MarketProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketProductRepository extends JpaRepository<MarketProductEntity, Integer> {
    // 카테고리 번호로 상품 조회
    List<MarketProductEntity> findByCategoryNo(int categoryNo);
    MarketProductEntity findByProductNo(int productNo);
}
