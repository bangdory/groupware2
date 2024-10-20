DROP TABLE community_notice;

create table community_notice(
                                 notice_no int primary key ,
                                 notice_title varchar(50) not null,
                                 notice_content varchar(2000) not null,
                                 notice_date date not null,
                                 notice_view int,
                                 emp_no VARCHAR(10),
                                 notice_state boolean,
                                 foreign key (emp_no) references employee (emp_no)
);
