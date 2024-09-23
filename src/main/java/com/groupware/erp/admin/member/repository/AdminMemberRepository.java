package com.groupware.erp.admin.member.repository;

import com.groupware.erp.admin.member.entity.AdminMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminMemberRepository extends JpaRepository<AdminMemberEntity, Long> {
}
