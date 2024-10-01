DROP TABLE Attendance;

CREATE TABLE Attendance (
                            att_no int AUTO_INCREMENT primary key ,
                            emp_no int,
                            reg_date date,
                            arr_time timestamp,
                            lev_time timestamp,
                            att_stat varchar(30)
);

CREATE TRIGGER insert_attendance_trigger
    AFTER INSERT ON employee
    FOR EACH ROW
BEGIN
    IF NEW.role = '일반 사용자' THEN
        INSERT INTO Attendance (emp_no) VALUES (NEW.emp_no);
END IF;
END;
