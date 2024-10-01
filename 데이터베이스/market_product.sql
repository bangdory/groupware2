create table market_product
(
    product_no  int not null primary key auto_increment,
    category_no int not null,
    name varchar(30) not null,
    price   int null,
    img varchar(50) null,
    description varchar(200) null,
    CONSTRAINT fk_category FOREIGN KEY (category_no)
        REFERENCES market_category(category_no)
);