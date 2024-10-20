package com.groupware.erp.market.model;

import com.groupware.erp.employee.entity.EmployeeEntity;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "market_cart")
public class MarketCartEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int no;

    @ManyToOne
    @JoinColumn(name = "emp_no", referencedColumnName = "emp_no")
    private EmployeeEntity employee;
    @Column(name = "product_no")
    private int productNo;
    @Column(name = "product_amount")
    private int productAmount;
}
