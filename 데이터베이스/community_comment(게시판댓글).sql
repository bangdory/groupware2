create table community_comment(
                                  comment_no int primary key ,
                                  post_no int not null,
                                  emp_no bigint,
                                  comment_content varchar(500) not null,
                                  comment_date date not null,
                                  comment_parent int not null, #부모 댓글번호
                                  comment_step int, #댓글 계층
                                  comment_ref int, #댓글 그룹
                                  comment_reforder int, #댓글 그룹 순서
                                  comment_answernum int, #댓글의 자식 댓글 수
                                  comment_state boolean,
                                  foreign key (emp_no) references employee (emp_no),
                                  foreign key (post_no) references community_post (post_no)
);
