package com.groupware.erp.comment.service;

import com.groupware.erp.comment.entity.CommunityCommentEntity;
import com.groupware.erp.comment.repository.CommunityCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityCommentService {

    @Autowired
    private CommunityCommentRepository communityCommentRepository;

    public CommunityCommentEntity
    saveComment(CommunityCommentEntity comment) {
        return communityCommentRepository.save(comment);
    }

    public List<CommunityCommentEntity> getAllCommentsByPost(int postNo) {
        return communityCommentRepository.findAllByPost_PostNo(postNo);
    }

    public List<CommunityCommentEntity> getRepliesByParentId(int parentCommentId) {
        return communityCommentRepository.findAllByCommentParent(parentCommentId);
    }

    public List<CommunityCommentEntity> getAllTopLevelCommentsByPost(int postNo) {
        return communityCommentRepository.findAllByPost_PostNoAndCommentParent(postNo, 0);
    }

    public Optional<CommunityCommentEntity> getCommentById(int id) {
        return communityCommentRepository.findById(id);
    }

    public List<CommunityCommentEntity> getAllComments() {
        return communityCommentRepository.findAll();
    }

    public void deleteComment(int id) {
        communityCommentRepository.deleteById(id);
    }

    // Update method
    public CommunityCommentEntity updateComment(int id, CommunityCommentEntity updatedComment) {
        return communityCommentRepository.findById(id).map(existingComment -> {
            existingComment.setCommentContent(updatedComment.getCommentContent());
            existingComment.setCommentDate(updatedComment.getCommentDate());
            existingComment.setCommentParent(updatedComment.getCommentParent());
            existingComment.setCommentStep(updatedComment.getCommentStep());
            existingComment.setCommentRef(updatedComment.getCommentRef());
            existingComment.setCommentRefOrder(updatedComment.getCommentRefOrder());
            existingComment.setCommentAnswerNum(updatedComment.getCommentAnswerNum());
            existingComment.setCommentState(updatedComment.getCommentState());
            return communityCommentRepository.save(existingComment);
        }).orElseThrow(() -> new RuntimeException("Comment not found with id " + id));
    }
}

