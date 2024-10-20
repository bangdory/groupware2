package com.groupware.erp.market.repository;


import com.groupware.erp.market.model.MarketCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketCartRepository extends JpaRepository<MarketCartEntity, Integer> {

    List<MarketCartEntity> findByEmployee_EmpNo(String empNo);
    void deleteByEmployee_EmpNoAndProductNo(String empNo, int productNo);
}
