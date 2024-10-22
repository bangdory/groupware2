-- auto-generated definition
create table market_discount
(
    discount_no   int auto_increment
        primary key,
    discount_name varchar(30)   null,
    product_no    int           null,
    discount_rate decimal(5, 2) null,
    start_date    date          null,
    end_date      date          null,
    constraint fk_discount_product
        foreign key (product_no) references market_product (product_no)
);

