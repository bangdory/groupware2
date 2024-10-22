package com.groupware.erp.market.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "market_product")
public class MarketProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no")
    private int productNo;
    @Column(name = "category_no")
    private int categoryNo;
    private String name;
    private int price;
    private String img;
    private String description;
}
