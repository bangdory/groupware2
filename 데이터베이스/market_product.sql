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

insert into market_product (category_no,name,price,description) VALUES (1,'건강해지는약',33000,'먹으면 머리가 자라남'),(1,'불사의약',99000,'먹으면 죽지않음'),(2,'RTX4090',1100000,'용산에서 직구함');