DROP TABLE Attendance;

CREATE TABLE Attendance (
                            att_no int AUTO_INCREMENT primary key ,
                            emp_no int,
                            reg_date date,
                            arr_time timestamp,
                            lev_time timestamp,
                            att_stat varchar(30)
);

ALTER TABLE attendance
    MODIFY COLUMN emp_no VARCHAR(10);


CREATE TRIGGER insert_attendance_trigger
    AFTER INSERT ON employee
    FOR EACH ROW
BEGIN
    IF NEW.role = '일반 사용자' THEN
        INSERT INTO Attendance (emp_no) VALUES (NEW.emp_no);
    END IF;
END;

-- att_stat을 ENUM으로 변경하고, work_time 칼럼 추가
ALTER TABLE Attendance
    MODIFY att_stat ENUM('미출근', '출근', '퇴근', '휴가') NOT NULL,
    ADD COLUMN work_time INT;

-- att_stat에서 '미출근'을 제거
ALTER TABLE Attendance
    MODIFY att_stat ENUM('출근', '퇴근', '휴가') NOT NULL;

ALTER TABLE attendance MODIFY COLUMN work_time TIME;

ALTER TABLE attendance
    MODIFY arr_time TIME,
    MODIFY lev_time TIME;

-- att_stat에서 '미출근'을 제거
ALTER TABLE Attendance
    MODIFY att_stat ENUM('출근', '퇴근', '휴가', '미출근') NOT NULL;

INSERT INTO attendance (emp_no, reg_date, arr_time, lev_time, att_stat, work_time)
VALUES
    ('0000000000', '2024-10-01', '08:30:00', '17:30:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-02', '09:00:00', '18:00:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-03', '08:45:00', '17:45:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-04', '09:15:00', '18:15:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-05', '08:30:00', '17:30:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-06', '09:00:00', '18:00:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-07', '08:30:00', '17:30:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-08', '09:00:00', '18:00:00', '퇴근', '09:00'),  -- 9시간 근무
    ('0000000000', '2024-10-09', null, null, '휴가', null),                 -- 휴가
    ('0000000000', '2024-10-10', null, null, '휴가', null);                 -- 휴가
