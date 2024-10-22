package com.groupware.erp.comment.repository;

import com.groupware.erp.comment.entity.CommunityCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommunityCommentRepository extends JpaRepository<CommunityCommentEntity, Integer> {
    List<CommunityCommentEntity> findAllByPost_PostNo(int postNo);
    List<CommunityCommentEntity> findAllByCommentParent(int commentParent);
    List<CommunityCommentEntity> findAllByPost_PostNoAndCommentParent(int postNo, int commentParent);
}
