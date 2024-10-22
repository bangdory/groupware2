package com.groupware.erp.market.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "market_receipt")
public class MarketReceiptEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_no")
    private int receiptNo;
    @Column(name = "emp_no")
    private String empNo;
    private int total_price;
    private LocalDate payment_date;

}
