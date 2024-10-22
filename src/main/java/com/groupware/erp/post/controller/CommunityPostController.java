package com.groupware.erp.post.controller;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.service.impl.EmployeeServiceImpl;
import com.groupware.erp.post.dto.CommunityPostDTO;
import com.groupware.erp.post.entity.CommunityPostEntity;
import com.groupware.erp.post.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class CommunityPostController {

    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;


    @GetMapping("/list")
    public String post() {
        return "post/postList";
    }

    @GetMapping("/createPage")
    public String create() {
        return "post/postCreate";
    }

    @GetMapping("/detailPage")
    public String detail() {
        return "post/postDetail";
    }

    @PostMapping("/create")
    public ResponseEntity<CommunityPostDTO> createPost(@RequestBody Map<String, Object> postData) {
        String empNo = (String) postData.get("empNo");

        EmployeeEntity employee = employeeServiceImpl.findEmployeeById(empNo);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 직원이 없으면 404 반환
        }

        // Create a new post entity
        CommunityPostEntity post = new CommunityPostEntity();
        post.setPostTitle((String) postData.get("postTitle"));
        post.setPostContent((String) postData.get("postContent"));
        post.setPostDate(LocalDate.parse((String) postData.get("postDate")));
        post.setPostView(0);
        post.setPostState(true);
        post.setEmployee(employee); // Set the employee

        // 저장
        CommunityPostEntity savedPost = communityPostService.savePost(post);
        CommunityPostDTO savedPostDTO = convertToDTO(savedPost); // Entity to DTO 변환
        return ResponseEntity.ok(savedPostDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommunityPostDTO> getPostById(@PathVariable int id) {
        Optional<CommunityPostEntity> post = communityPostService.getPostById(id);
        return post.map(p -> ResponseEntity.ok(convertToDTO(p)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CommunityPostDTO>> getAllPosts() {
        List<CommunityPostEntity> posts = communityPostService.getAllPosts();
        List<CommunityPostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO) // Entity to DTO 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        communityPostService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    // Update API
    @PutMapping("/{id}")
    public ResponseEntity<CommunityPostDTO> updatePost(@PathVariable int id, @RequestBody CommunityPostDTO updatedPostDTO) {
        try {
            CommunityPostEntity updatedPost = convertToEntity(updatedPostDTO); // DTO to Entity 변환
            CommunityPostEntity updated = communityPostService.updatePost(id, updatedPost);
            return ResponseEntity.ok(convertToDTO(updated)); // Entity to DTO 변환
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Entity -> DTO 변환 메서드
    private CommunityPostDTO convertToDTO(CommunityPostEntity postEntity) {
        CommunityPostDTO dto = new CommunityPostDTO();
        dto.setPostNo(postEntity.getPostNo());
        dto.setPostTitle(postEntity.getPostTitle());
        dto.setPostContent(postEntity.getPostContent());
        dto.setPostDate(postEntity.getPostDate());
        dto.setPostView(postEntity.getPostView());
        dto.setPostState(postEntity.getPostState());
        dto.setEmpNo(postEntity.getEmployee().getEmpNo());
        dto.setEmpName(postEntity.getEmployee().getEmpName());
        dto.setEmpEmail(postEntity.getEmployee().getEmpEmail());
        dto.setEmpPhone(postEntity.getEmployee().getEmpPhone());
//        dto.setEmpHireDate(postEntity.getEmployee().getEmpHireDate());
        dto.setDepartment(postEntity.getEmployee().getDepartment());
        dto.setEmpGrade(postEntity.getEmployee().getEmpGrade());
        return dto;
    }

    // DTO -> Entity 변환 메서드
    private CommunityPostEntity convertToEntity(CommunityPostDTO postDTO) {
        CommunityPostEntity entity = new CommunityPostEntity();
        entity.setPostNo(postDTO.getPostNo());
        entity.setPostTitle(postDTO.getPostTitle());
        entity.setPostContent(postDTO.getPostContent());
        entity.setPostDate(postDTO.getPostDate());
        entity.setPostView(postDTO.getPostView());
        entity.setPostState(postDTO.getPostState());
        // Employee 정보는 별도로 처리 필요 (ex: setEmployee)
        return entity;
    }
}
//
//    @GetMapping("/list")
//    public String post () {
//        return "post/postList";
//    }
//    @GetMapping("/createPage")
//    public String create () {
//        return "post/postCreate";
//    }
//
//    @GetMapping("/detailPage")
//    public String detail () {
//        return "post/postDetail";
//    }
//
//    @PostMapping("/create")
//    public ResponseEntity<CommunityPostEntity> createPost(@RequestBody Map<String, Object> postData) {
//        String empNo = (String) postData.get("empNo");
//
//        System.out.println(empNo);
//
//        EmployeeEntity employee = employeeServiceImpl.findEmployeeById(empNo);
//
//        System.out.println(postData);
//        System.out.println(employee);
//
//        if (employee == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // 직원이 없으면 404 반환
//        }
//
//        // Create a new post entity
//        CommunityPostEntity post = new CommunityPostEntity();
//        post.setPostTitle((String) postData.get("postTitle"));
//        post.setPostContent((String) postData.get("postContent"));
//        post.setPostDate(LocalDate.parse((String) postData.get("postDate")));
//        post.setPostView(0);
//        post.setPostState(true);
//        post.setEmployee(employee); // Set the employee
//
//        // 저장
//        CommunityPostEntity savedPost = communityPostService.savePost(post);
//        return ResponseEntity.ok(savedPost);
//    }
//
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CommunityPostEntity> getPostById(@PathVariable int id) {
//        return communityPostService.getPostById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CommunityPostEntity>> getAllPosts() {
//        return ResponseEntity.ok(communityPostService.getAllPosts());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deletePost(@PathVariable int id) {
//        communityPostService.deletePost(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    // Update API
//    @PutMapping("/{id}")
//    public ResponseEntity<CommunityPostEntity> updatePost(@PathVariable int id, @RequestBody CommunityPostEntity updatedPost) {
//        try {
//            CommunityPostEntity updated = communityPostService.updatePost(id, updatedPost);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}