CREATE INDEX idx_hire_date ON employee(emp_hiredate);


CREATE TABLE annualLeave (
                             emp_no varchar(10) NOT NULL,  -- 직원 번호 (외래키)
                             emp_hiredate DATE NOT NULL,  -- 입사일 (외래키)
                             total_ann INT NOT NULL,  -- 총 연차
                             use_ann INT NOT NULL,  -- 사용한 연차
                             PRIMARY KEY (emp_no, emp_hiredate),  -- 복합 기본키 (emp_no, emp_hiredate)
                             CONSTRAINT fk_employee_emp_no FOREIGN KEY (emp_no) REFERENCES employee(emp_no),  -- emp_no 외래키 설정
                             CONSTRAINT fk_employee_emp_hiredate FOREIGN KEY (emp_hiredate) REFERENCES employee(emp_hiredate)  -- emp_hiredate 외래키 설정
);

# emp 테이블에 데이터 추가 시 annualLeave에 데이터를 입력하는 트리거
DELIMITER $$

CREATE TRIGGER trg_insert_annualLeave
    AFTER INSERT ON employee
    FOR EACH ROW
BEGIN
    INSERT INTO annualLeave (emp_no, emp_hiredate, total_ann, use_ann)
    VALUES (NEW.emp_no, NEW.emp_hiredate, 11, 0);
END$$

DELIMITER ;


INSERT INTO annualLeave (emp_no, emp_hiredate, total_ann, use_ann , rem_ann, pending_ann) values ('0000000000', '2024-10-10', 11, 0,0,0);
INSERT INTO annualLeave (emp_no, emp_hiredate, total_ann, use_ann) values ('0000000001', '2024-10-10', 11, 0);


# 만약 1년차로 연차 발급 시 남은 잔여 연차를 입력하기 위한 남은 연차 칼럼 추가
ALTER TABLE annualLeave
    ADD COLUMN rem_ann INT NOT NULL;

# 연차 수당을 위한 미처리된 잔여 연차 칼럼 추가
ALTER TABLE annualLeave
    ADD COLUMN pending_ann INT NOT NULL;