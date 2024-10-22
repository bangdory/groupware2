package com.groupware.erp.market.service;


import com.groupware.erp.market.model.MarketCategoryEntity;
import com.groupware.erp.market.repository.MarketCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class MarketCategoryService {
    @Autowired
    private MarketCategoryRepository marketCategoryRepository;
    // 모든 카테고리 가져오기
    public List<MarketCategoryEntity> findAll() {
        return marketCategoryRepository.findAll();
    }

    // 카테고리 추가 또는 업데이트
    public MarketCategoryEntity saveOrUpdate(MarketCategoryEntity category) {
        return marketCategoryRepository.save(category);
    }


    // 카테고리 삭제
    public void deleteById(int id) {
        marketCategoryRepository.deleteById(id);
    }

    // 카테고리 이름 변경
    public MarketCategoryEntity updateCategoryName(int id, String newName) {
        Optional<MarketCategoryEntity> categoryOpt = marketCategoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            MarketCategoryEntity category = categoryOpt.get();
            category.setCategoryName(newName);
            return marketCategoryRepository.save(category);
        }
        return null; // 카테고리가 없을 경우 null 반환
    }
    // 현재 순서의 카테고리 가져오기
    public void changeCategoryOrder(int currentOrderNo, int newOrderNo) {
        MarketCategoryEntity currentCategory = marketCategoryRepository.findByOrderNo(currentOrderNo);
        MarketCategoryEntity newCategory = marketCategoryRepository.findByOrderNo(newOrderNo);

        if (currentCategory != null && newCategory != null) {
            // 현재 카테고리의 순서 변경
            currentCategory.setOrderNo(newOrderNo);
            // 새 위치에 있는 카테고리의 순서 변경
            newCategory.setOrderNo(currentOrderNo);

            // 업데이트 수행
            marketCategoryRepository.save(currentCategory);
            marketCategoryRepository.save(newCategory);
        }
    }

}
