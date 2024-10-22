package com.groupware.erp.comment.controller;


import com.groupware.erp.comment.dto.CommunityCommentDTO;
import com.groupware.erp.comment.entity.CommunityCommentEntity;
import com.groupware.erp.comment.service.CommunityCommentService;
import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.service.impl.EmployeeServiceImpl;
import com.groupware.erp.post.entity.CommunityPostEntity;
import com.groupware.erp.post.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/comments")
public class CommunityCommentController {

    @Autowired
    private CommunityCommentService communityCommentService;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;
    @Autowired
    private CommunityPostService communityPostService;

    // 댓글 및 대댓글 생성
    @PostMapping
    public ResponseEntity<CommunityCommentDTO> createComment(@RequestBody Map<String, Object> commentData) {
        String empNo = (String) commentData.get("empNo");
        int postNo = (int) commentData.get("postNo");

        // Fetch the employee entity
        EmployeeEntity employee = employeeServiceImpl.findEmployeeById(empNo);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if employee not found
        }

        // Fetch the post entity
        CommunityPostEntity post = communityPostService.getPostById(postNo)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        // Create a new comment entity
        CommunityCommentEntity comment = new CommunityCommentEntity();
        comment.setCommentContent((String) commentData.get("commentContent"));
        comment.setCommentDate(LocalDate.now()); // Set the current date for the comment
        comment.setCommentParent((int) commentData.get("commentParent")); // For top-level comment, it should be 0
        comment.setCommentStep((int) commentData.getOrDefault("commentStep", 0)); // Default to 0 if not provided
        comment.setCommentRef(post.getPostNo()); // Set the reference to the post
        comment.setCommentRefOrder((int) commentData.getOrDefault("commentRefOrder", 0)); // Default to 0 if not provided
        comment.setCommentAnswerNum((int) commentData.getOrDefault("commentAnswernum", 0)); // Default to 0 if not provided
        comment.setCommentState(true); // Set comment state to active

        // Set relationships
        comment.setEmployee(employee); // Associate the comment with the employee
        comment.setPost(post); // Associate the comment with the post

        // Save the comment
        CommunityCommentEntity savedComment = communityCommentService.saveComment(comment);

        // Convert entity to DTO
        CommunityCommentDTO commentDTO = convertToDTO(savedComment);

        return ResponseEntity.ok(commentDTO);
    }

    // 특정 게시글의 모든 댓글 가져오기
    @GetMapping("/post/{postNo}")
    public ResponseEntity<List<CommunityCommentDTO>> getCommentsByPost(@PathVariable int postNo) {
        List<CommunityCommentEntity> comments = communityCommentService.getAllCommentsByPost(postNo);

        // Convert entities to DTOs
        List<CommunityCommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentDTOs);
    }

    @GetMapping("/post/{postNo}/top-level")
    public ResponseEntity<List<CommunityCommentDTO>> getTopLevelCommentsByPost(@PathVariable int postNo) {
        List<CommunityCommentEntity> comments = communityCommentService.getAllTopLevelCommentsByPost(postNo);

        // Convert entities to DTOs
        List<CommunityCommentDTO> commentDTOs = comments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(commentDTOs);
    }

    // 대댓글 가져오기
    @GetMapping("/replies/{parentCommentId}")
    public ResponseEntity<List<CommunityCommentDTO>> getRepliesByParentComment(@PathVariable int parentCommentId) {
        List<CommunityCommentEntity> replies = communityCommentService.getRepliesByParentId(parentCommentId);

        // Convert entities to DTOs
        List<CommunityCommentDTO> replyDTOs = replies.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(replyDTOs);
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommunityCommentDTO> updateComment(@PathVariable int id, @RequestBody CommunityCommentEntity updatedComment) {
        try {
            CommunityCommentEntity updated = communityCommentService.updateComment(id, updatedComment);

            // Convert entity to DTO
            CommunityCommentDTO updatedDTO = convertToDTO(updated);

            return ResponseEntity.ok(updatedDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
        communityCommentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }

    // Helper method to convert entity to DTO
    private CommunityCommentDTO convertToDTO(CommunityCommentEntity entity) {
        CommunityCommentDTO dto = new CommunityCommentDTO();

        // Set DTO fields from entity
        dto.setCommentNo(entity.getCommentNo());
        dto.setPostNo(entity.getPost().getPostNo());
        dto.setPostTitle(entity.getPost().getPostTitle());
        dto.setPostContent(entity.getPost().getPostContent());
        dto.setPostDate(entity.getPost().getPostDate());
        dto.setPostView(entity.getPost().getPostView());
        dto.setPostState(entity.getPost().getPostState());
        dto.setEmpNo(entity.getEmployee().getEmpNo());
        dto.setEmpName(entity.getEmployee().getEmpName());
        dto.setCommentContent(entity.getCommentContent());
        dto.setCommentDate(entity.getCommentDate());
        dto.setCommentParent(entity.getCommentParent());
        dto.setCommentStep(entity.getCommentStep());
        dto.setCommentRef(entity.getCommentRef());
        dto.setCommentRefOrder(entity.getCommentRefOrder());
        dto.setCommentAnswerNum(entity.getCommentAnswerNum());
        dto.setCommentState(entity.getCommentState());

        return dto;
    }
}

//    // 댓글 및 대댓글 생성
//    @PostMapping
//    public ResponseEntity<CommunityCommentEntity> createComment(@RequestBody Map<String, Object> commentData) {
//        String empNo = (String) commentData.get("empNo");
//        int postNo = (int) commentData.get("postNo");
//
//        // Fetch the employee entity
//        EmployeeEntity employee = employeeServiceImpl.findEmployeeById(empNo);
//        if (employee == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Return 404 if employee not found
//        }
//
//        // Fetch the post entity
//        CommunityPostEntity post = communityPostService.getPostById(postNo)
//                .orElseThrow(() -> new RuntimeException("Post not found"));
//
//        // Create a new comment entity
//        CommunityCommentEntity comment = new CommunityCommentEntity();
//        comment.setCommentContent((String) commentData.get("commentContent"));
//        comment.setCommentDate(LocalDate.now()); // Set the current date for the comment
//        comment.setCommentParent((int) commentData.get("commentParent")); // For top-level comment, it should be 0
//        comment.setCommentStep((int) commentData.getOrDefault("commentStep", 0)); // Default to 0 if not provided
//        comment.setCommentRef(post.getPostNo()); // Set the reference to the post
//        comment.setCommentRefOrder((int) commentData.getOrDefault("commentRefOrder", 0)); // Default to 0 if not provided
//        comment.setCommentAnswerNum((int) commentData.getOrDefault("commentAnswernum", 0)); // Default to 0 if not provided
//        comment.setCommentState(true); // Set comment state to active
//
//        // Set relationships
//        comment.setEmployee(employee); // Associate the comment with the employee
//        comment.setPost(post); // Associate the comment with the post
//
//        // Save the comment
//        CommunityCommentEntity savedComment = communityCommentService.saveComment(comment);
//
//        return ResponseEntity.ok(savedComment);
//    }
//
//
//    // 특정 게시글의 모든 댓글 가져오기
//    @GetMapping("/post/{postNo}")
//    public ResponseEntity<List<CommunityCommentEntity>> getCommentsByPost(@PathVariable int postNo) {
//        List<CommunityCommentEntity> comments = communityCommentService.getAllCommentsByPost(postNo);
//        return ResponseEntity.ok(comments);
//    }
//
//    @GetMapping("/post/{postNo}/top-level")
//    public ResponseEntity<List<CommunityCommentEntity>> getTopLevelCommentsByPost(@PathVariable int postNo) {
//        List<CommunityCommentEntity> comments = communityCommentService.getAllTopLevelCommentsByPost(postNo);
//        return ResponseEntity.ok(comments);
//    }
//
//    // 대댓글 가져오기
//    @GetMapping("/replies/{parentCommentId}")
//    public ResponseEntity<List<CommunityCommentEntity>> getRepliesByParentComment(@PathVariable int parentCommentId) {
//        List<CommunityCommentEntity> replies = communityCommentService.getRepliesByParentId(parentCommentId);
//        return ResponseEntity.ok(replies);
//    }
//
//    // 댓글 수정
//    @PutMapping("/{id}")
//    public ResponseEntity<CommunityCommentEntity> updateComment(@PathVariable int id, @RequestBody CommunityCommentEntity updatedComment) {
//        try {
//            CommunityCommentEntity updated = communityCommentService.updateComment(id, updatedComment);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteComment(@PathVariable int id) {
//        communityCommentService.deleteComment(id);
//        return ResponseEntity.noContent().build();
//    }
//}