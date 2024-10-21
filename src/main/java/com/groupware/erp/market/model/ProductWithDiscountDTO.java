package com.groupware.erp.market.model;

import com.groupware.erp.market.model.MarketProductEntity;
import lombok.Data;

import java.math.BigDecimal;

// DTO 클래스 (데이터 전송용 객체)
@Data
public class ProductWithDiscountDTO {
    private MarketProductEntity product;
    private BigDecimal discountedPrice;

    public ProductWithDiscountDTO(MarketProductEntity product, BigDecimal discountedPrice) {
        this.product = product;
        this.discountedPrice = discountedPrice;
    }
}