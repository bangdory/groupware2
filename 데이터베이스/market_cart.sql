-- auto-generated definition
create table market_cart
(
    no             int auto_increment
        primary key,
    emp_no         varchar(255) not null,
    product_no     int         not null,
    product_amount int         null,

    constraint fk_cart_product_no
        foreign key (product_no) references market_product (product_no)
);

