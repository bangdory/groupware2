package com.groupware.erp.notice.repository;

import com.groupware.erp.notice.entity.CommunityNoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityNoticeRepository extends JpaRepository<CommunityNoticeEntity, Integer> {
}