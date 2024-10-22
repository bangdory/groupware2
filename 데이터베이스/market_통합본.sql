-- auto-generated definition
create table market_category
(
    category_no   int auto_increment
        primary key,
    category_name varchar(30) not null,
    order_no      int         null
);

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
create table market_cart
(
    no             int auto_increment
        primary key,
    emp_no         varchar(255) not null,
    product_no     int          not null,
    product_amount int          null,
    name           varchar(50)  null,
    price          int          null,
    constraint fk_cart_product_no
        foreign key (product_no) references market_product (product_no)
);

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

INSERT INTO market_category (category_no,category_name, order_no)
VALUES (1,'건강', 1),(2,'전자기기',2),(3,'추석세트',3);

insert into market_product (category_no,name,price,description) VALUES (1,'건강해지는약',33000,'먹으면 머리가 자라남'),(1,'불사의약',99000,'먹으면 죽지않음'),(2,'RTX4090',1100000,'용산에서 직구함');
