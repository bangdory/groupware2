-- auto-generated definition
create table market_category
(
    category_no   int auto_increment
        primary key,
    category_name varchar(30) not null,
    order_no      int         null
);

INSERT INTO market_category (category_no,category_name, order_no)
VALUES (1,'건강', 1),(2,'전자기기',2),(3,'추석세트',3);
