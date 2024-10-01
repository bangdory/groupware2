create table community_post(
                               post_no int primary key ,
                               post_title varchar(100) not null,
                               post_content varchar(2000) not null,
                               post_date date not null,
                               post_view int,
                               emp_no bigint,
                               foreign key (emp_no) references employee (emp_no)
);

alter table community_post add column post_state boolean;
