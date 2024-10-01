create table market_receipt(
    receipt_no int not null auto_increment primary key ,
    emp_no bigint not null,
    total_price int not null,
    payment_date date not null,
    CONSTRAINT fk_mk_emp_no FOREIGN KEY (emp_no) REFERENCES employee(emp_no)

);