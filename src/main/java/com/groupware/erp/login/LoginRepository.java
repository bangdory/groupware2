package com.groupware.erp.login;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface LoginRepository extends JpaRepository<LoginEntity, String> {

    @Query("SELECT e FROM LoginEntity e WHERE e.empNo = :empNo")
    LoginEntity findByEmpNo(String empNo);

    @Modifying
    @Query("UPDATE LoginEntity e set e.empPassword = :newPassword where e.empNo = :empNo")
    int updatePassword(String empNo, String newPassword);
}
