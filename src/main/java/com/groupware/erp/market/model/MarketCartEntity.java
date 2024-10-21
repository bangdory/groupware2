package com.groupware.erp.market.model;

import com.groupware.erp.employee.entity.EmployeeEntity;
import jakarta.persistence.*;
import lombok.Cleanup;
import lombok.Data;

@Data
@Entity
@Table(name = "market_cart")
public class MarketCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;


    @Column(name = "emp_no")
    private String empNo;
    @Column(name = "product_no")
    private int productNo;
    @Column(name = "product_amount")
    private int productAmount;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private int price;

}
