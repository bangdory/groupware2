create table file(
                     file_no int primary key ,
                     post_no int,
                     notice_no int,
                     original_filename varchar(100),
                     file_name varchar(100),
                     file_size varchar(100),
                     foreign key (post_no) references community_post (post_no),
                     foreign key (notice_no) references community_notice (notice_no)
);