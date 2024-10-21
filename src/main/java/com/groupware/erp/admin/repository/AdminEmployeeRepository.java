package com.groupware.erp.admin.repository;

import com.groupware.erp.admin.entity.AdminEmployeeEntity;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AdminEmployeeRepository extends JpaRepository<AdminEmployeeEntity, String> {

    Optional<AdminEmployeeEntity> findByEmpNo(String empNo);

    @Modifying
    @Query("UPDATE AdminEmployeeEntity e SET e.department = :status, e.empGrade = :status WHERE e.empNo = :empNo")
    void updateEmployeeStatus(@Param("empNo") String empNo, @Param("status") String status);

//    @Modifying
//    @Query("DELETE FROM AdminEmployeeEntity e WHERE e.empNo = :empNo")
//    void updateEmployeeStatus(@Param("empNo") String empNo);

}
