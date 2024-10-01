create table market_cart (
    no int not null primary key auto_increment,
    emp_no bigint not null,
    product_no int not null,
                         CONSTRAINT fk_cart_emp_no FOREIGN KEY (emp_no) REFERENCES employee(emp_no),
                         CONSTRAINT fk_cart_product_no FOREIGN KEY (product_no) REFERENCES  market_product(product_no)
);