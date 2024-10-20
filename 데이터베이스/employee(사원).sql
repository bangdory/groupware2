drop table if exists member;
drop table if exists employee;
create table employee(
                         emp_no varchar(10) primary key,
                         emp_password varchar(255) not null,
                         emp_email varchar(50) unique not null,
                         emp_name varchar(10) not null,
                         emp_phone varchar(15),
                         emp_hiredate date not null, -- 입사일
                         department enum('영업팀', '인사팀', '마케팅팀') not null, -- 부서
                         emp_grade enum('사원', '팀장', '이사', '대표이사') not null, -- 직급
                         role enum('USER', 'ADMIN')
);

-- role 열의 ENUM 타입을 'ADMIN', 'USER'로 변경
ALTER TABLE employee
    MODIFY role ENUM('ADMIN', 'USER');

-- emp_no 열의 타입을 VARCHAR(10)으로 변경
ALTER TABLE employee
    MODIFY emp_no VARCHAR(10);

-- password 자동으로 설정하는 트리거(24.10.17. 테스트용으로 추가)
Delimiter //
create trigger setPassword
    before insert on employee
    for each row
begin
    set new.emp_password = new.emp_no;
end;
Delimiter ;


-- 직원 퇴사 시 정보를 삭제하지 않고 직급/부서를 '퇴사' 상태로 변경하기 위해서 enum 수정
ALTER TABLE employee MODIFY COLUMN department ENUM('영업팀', '인사팀', '마케팅팀', '퇴사') not null;
ALTER TABLE employee MODIFY COLUMN emp_grade ENUM('사원', '팀장', '이사', '대표이사', '퇴사') not null;