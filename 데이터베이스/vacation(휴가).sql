-- 휴가 테이블
create table vacation
(
    vac_no           int auto_increment
        primary key,
    emp_no           int          null,
    req_date         date         null,
    start_date       date         null,
    end_date         date         null,
    approve_boolean  tinyint(1)   null,
    approve_date     date         null,
    request_reason   varchar(200) null,
    rejection_reason varchar(200) null,
    authorizer       int          null
);



ALTER TABLE vacation
    ADD totalDays INT NULL;

ALTER TABLE vacation
    RENAME COLUMN totalDays TO total_days;

ALTER TABLE vacation
    MODIFY COLUMN emp_no VARCHAR(10) NOT NULL ;