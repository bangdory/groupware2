package com.groupware.erp.market.model;


import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "market_discount")
public class MarketDiscountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "discount_no")
    private int discountNo;
    @Column(name = "discount_name")
    private String discountName;
    @Column(name = "product_no")
    private int productNo;

    @Column(precision = 5, scale = 2) // DECIMAL(5,2)로 매핑
    private BigDecimal discount_rate; // 할인율 (백분율)

    private LocalDate start_date;
    private LocalDate end_date;
}
