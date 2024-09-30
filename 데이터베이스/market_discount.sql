create table market_discount(
    discount_no int not null auto_increment primary key,
    discount_name varchar(30) null,
    product_no int null,
    discount_rate DECIMAL(5, 2),
    start_date DATE,
    end_date DATE,
    CONSTRAINT fk_discount_product FOREIGN KEY (product_no) REFERENCES market_product(product_no)
)