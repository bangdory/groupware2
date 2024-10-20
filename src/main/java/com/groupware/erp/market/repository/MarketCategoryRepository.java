package com.groupware.erp.market.repository;

import com.groupware.erp.market.model.MarketCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketCategoryRepository extends JpaRepository<MarketCategoryEntity, Integer> {


    MarketCategoryEntity findByOrderNo(int orderNo); // 추가
}
