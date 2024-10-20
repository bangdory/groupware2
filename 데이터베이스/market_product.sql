-- auto-generated definition
create table market_product
(
    product_no  int auto_increment
        primary key,
    category_no int          not null,
    name        varchar(30)  not null,
    price       int          null,
    img         varchar(50)  null,
    description varchar(200) null,
    constraint fk_category
        foreign key (category_no) references market_category (category_no)
);

