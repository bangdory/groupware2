package com.groupware.erp.market.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "market_category")
public class MarketCategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_no")
    private int categoryNo;
    @Column(name = "category_name")
    private String categoryName;
    @Column(name = "order_no")
    private int orderNo;
}
