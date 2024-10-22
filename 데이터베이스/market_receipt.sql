-- auto-generated definition
create table market_receipt
(
    receipt_no   int auto_increment
        primary key,
    emp_no       varchar(255) not null,
    total_price  int         not null,
    payment_date date        not null,
    constraint fk_mk_emp_no
        foreign key (emp_no) references employee (emp_no)
);

