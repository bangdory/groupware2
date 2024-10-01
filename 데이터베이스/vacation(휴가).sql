-- 휴가 테이블

CREATE TABLE vacation (
                          vac_no int auto_increment primary key , -- 고유 넘버
                          emp_no int,                             -- 사원 번호
                          req_date date,                          -- 신청 날짜
                          start_date date,                        -- 휴가 시작 날짜
                          end_date date,                          -- 휴가 종료 날짜
                          approve_boolean boolean,                -- 허가 여부
                          approve_date date,                      -- 허가 날짜
                          reason VARCHAR(200),                    -- 거절 사유
                          authorizer int                          -- 허가자
);