package com.groupware.erp.post.repository;

import com.groupware.erp.post.entity.CommunityPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPostEntity, Integer> {
}