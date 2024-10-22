package com.groupware.erp.post.service;

import com.groupware.erp.post.entity.CommunityPostEntity;
import com.groupware.erp.post.repository.CommunityPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityPostService {

    @Autowired
    private CommunityPostRepository communityPostRepository;

    public CommunityPostEntity savePost(CommunityPostEntity post) {
        return communityPostRepository.save(post);
    }

    public Optional<CommunityPostEntity> getPostById(int id) {
        return communityPostRepository.findById(id);
    }

    public List<CommunityPostEntity> getAllPosts() {
        return communityPostRepository.findAll();
    }

    public void deletePost(int id) {
        communityPostRepository.deleteById(id);
    }

    // Update method
    public CommunityPostEntity updatePost(int id, CommunityPostEntity updatedPost) {
        return communityPostRepository.findById(id).map(existingPost -> {
            existingPost.setPostTitle(updatedPost.getPostTitle());
            existingPost.setPostContent(updatedPost.getPostContent());
            existingPost.setPostDate(updatedPost.getPostDate());
            existingPost.setPostView(updatedPost.getPostView());
            existingPost.setPostState(updatedPost.getPostState());
            return communityPostRepository.save(existingPost);
        }).orElseThrow(() -> new RuntimeException("Post not found with id " + id));
    }
}