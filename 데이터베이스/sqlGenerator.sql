create table employee
(
    emp_no       varchar(255)            not null
        primary key,
    emp_password varchar(255)           not null,
    emp_email    varchar(255)            not null,
    emp_name     varchar(255)           null,
    emp_phone    varchar(255)           null,
    emp_hiredate date                   not null,
    department   varchar(255)           null,
    emp_grade    varchar(255)           null,
    role         enum ('ADMIN', 'USER') null,
    constraint emp_email
        unique (emp_email)
);

create index idx_employee_emp_hiredate
    on employee (emp_hiredate);


create table annualleave
(
    emp_no       varchar(255)   not null,
    emp_hiredate date          not null,
    total_ann    int default 0 not null,
    use_ann      int default 0 not null,
    rem_ann      int default 0 not null,
    pending_ann  int default 0 not null,
    primary key (emp_no, emp_hiredate),
    constraint fk_employee_emp_hiredate
        foreign key (emp_hiredate) references employee (emp_hiredate),
    constraint fk_employee_emp_no
        foreign key (emp_no) references employee (emp_no)
);

create index idx_ann_leave_emp_hiredate
    on annualleave (emp_hiredate);

create index idx_ann_leave_emp_no
    on annualleave (emp_no);

create table attendance
(
    att_no    bigint auto_increment
        primary key,
    emp_no    varchar(255) null,
    reg_date  date         null,
    arr_time  time         null,
    lev_time  time         null,
    att_stat  varchar(255) null,
    work_time time         null,
    constraint FKapreal7bfnyphaunsmqtu2bdj
        foreign key (emp_no) references employee (emp_no)
);

create table community_notice
(
    notice_no      int           not null
        primary key,
    notice_title   varchar(255)   not null,
    notice_content varchar(2000) not null,
    notice_date    date          not null,
    notice_view    int           null,
    emp_no         varchar(255)   null,
    notice_state   tinyint(1)    null,
    constraint community_notice_ibfk_1
        foreign key (emp_no) references employee (emp_no)
);

create index emp_no
    on community_notice (emp_no);

create table community_post
(
    post_no      int           not null
        primary key,
    post_title   varchar(100)  not null,
    post_content varchar(2000) not null,
    post_date    date          not null,
    post_view    int           null,
    emp_no       varchar(255)   null,
    post_state   tinyint(1)    null,
    constraint community_post_ibfk_1
        foreign key (emp_no) references employee (emp_no)
);

create index emp_no
    on community_post (emp_no);

create index idx_hire_date
    on employee (emp_hiredate);

create definer = root@localhost trigger trg_insert_annualLeave
    after insert
    on employee
    for each row
BEGIN
    INSERT INTO annualLeave (emp_no, emp_hiredate, total_ann, use_ann)
    VALUES (NEW.emp_no, NEW.emp_hiredate, 11, 0);
END;

create table mail
(
    id                    int auto_increment
        primary key,
    create_date           datetime(6)  null,
    message               text         null,
    receiver_mail_address varchar(255) null,
    subject               text         null
);

create table member
(
    member_no       bigint auto_increment
        primary key,
    member_email    varchar(255)  not null,
    member_password varchar(255) null,
    member_name     varchar(255) null,
    role            varchar(255)  null,
    constraint member_email
        unique (member_email)
);

create table vacation
(
    vac_no           bigint       not null
        primary key,
    emp_no           varchar(255) null,
    req_date         date         null,
    start_date       date         null,
    end_date         date         null,
    approve_boolean  int          null,
    approve_date     date         null,
    request_reason   varchar(255) null,
    rejection_reason varchar(255) null,
    authorizer       int          null,
    total_days       int          null
);

