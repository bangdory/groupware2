package com.groupware.erp.file.controller;

import com.groupware.erp.file.dto.FileDTO;
import com.groupware.erp.file.entity.FileEntity;
import com.groupware.erp.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    // 파일 업로드
    @PostMapping("/upload")
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file,
                                              @RequestParam(value = "postNo", required = false, defaultValue = "0") int postNo,
                                              @RequestParam(value = "noticeNo", required = false, defaultValue = "0") int noticeNo) {
        try {
            FileEntity savedFile = fileService.saveFile(file, postNo, noticeNo);
            FileDTO savedFileDTO = convertToDTO(savedFile); // Entity를 DTO로 변환
            return ResponseEntity.ok(savedFileDTO);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // 특정 파일 조회
    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFileById(@PathVariable int id) {
        Optional<FileEntity> file = fileService.getFileById(id);
        return file.map(f -> ResponseEntity.ok(convertToDTO(f)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/post/{postNo}")
    public ResponseEntity<List<FileDTO>> getFilesByPostNo(@PathVariable int postNo) {
        List<FileEntity> files = fileService.getFilesByPostNo(postNo);
        List<FileDTO> fileDTOs = files.stream()
                .map(this::convertToDTO) // Entity를 DTO로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileDTOs);
    }

    @GetMapping("/notice/{noticeNo}")
    public ResponseEntity<List<FileDTO>> getFilesByNoticeNo(@PathVariable int noticeNo) {
        List<FileEntity> files = fileService.getFilesByNoticeNo(noticeNo);
        List<FileDTO> fileDTOs = files.stream()
                .map(this::convertToDTO) // Entity를 DTO로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileDTOs);
    }

    // 모든 파일 조회
    @GetMapping
    public ResponseEntity<List<FileDTO>> getAllFiles() {
        List<FileEntity> files = fileService.getAllFiles();
        List<FileDTO> fileDTOs = files.stream()
                .map(this::convertToDTO) // Entity를 DTO로 변환
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileDTOs);
    }

    // 파일 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable int id) {
        fileService.deleteFile(id);
        return ResponseEntity.noContent().build();
    }

    // Entity를 DTO로 변환하는 메서드
    private FileDTO convertToDTO(FileEntity fileEntity) {
        FileDTO fileDTO = new FileDTO();
        fileDTO.setFileNo(fileEntity.getFileNo());
        fileDTO.setOriginalFilename(fileEntity.getOriginalFilename());
        fileDTO.setFileName(fileEntity.getFileName());
        fileDTO.setFileSize(fileEntity.getFileSize());

        // 필요한 경우 다른 관련 엔티티 정보도 변환하여 설정
        if (fileEntity.getNotice() != null) {
            fileDTO.setNoticeNo(fileEntity.getNotice().getNoticeNo());
            fileDTO.setNoticeTitle(fileEntity.getNotice().getNoticeTitle());
            fileDTO.setNoticeContent(fileEntity.getNotice().getNoticeContent());
            fileDTO.setNoticeDate(fileEntity.getNotice().getNoticeDate());
            fileDTO.setNoticeView(fileEntity.getNotice().getNoticeView());
            fileDTO.setNoticeState(fileEntity.getNotice().getNoticeState());
        }

        if (fileEntity.getPost() != null) {
            fileDTO.setPostNo(fileEntity.getPost().getPostNo());
            fileDTO.setPostTitle(fileEntity.getPost().getPostTitle());
            fileDTO.setPostContent(fileEntity.getPost().getPostContent());
            fileDTO.setPostDate(fileEntity.getPost().getPostDate());
            fileDTO.setPostView(fileEntity.getPost().getPostView());
            fileDTO.setPostState(fileEntity.getPost().getPostState());
        }

        return fileDTO;
    }
}
//
//    // 파일 업로드
//    @PostMapping("/upload")
//    public ResponseEntity<FileEntity> uploadFile(@RequestParam("file") MultipartFile file,
//                                                 @RequestParam(value = "postNo", required = false, defaultValue = "0") int postNo,
//                                                 @RequestParam(value = "noticeNo", required = false, defaultValue = "0") int noticeNo) {
//        try {
//            FileEntity savedFile = fileService.saveFile(file, postNo, noticeNo);
//            return ResponseEntity.ok(savedFile);
//        } catch (IOException e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
//
//
//
//    // 특정 파일 조회
//    @GetMapping("/{id}")
//    public ResponseEntity<FileEntity> getFileById(@PathVariable int id) {
//        Optional<FileEntity> file = fileService.getFileById(id);
//        return file.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping("/post/{postNo}")
//    public ResponseEntity<List<FileEntity>> getFilesByPostNo(@PathVariable int postNo) {
//        List<FileEntity> files = fileService.getFilesByPostNo(postNo);
//        return ResponseEntity.ok(files);
//    }
//
//    @GetMapping("/notice/{noticeNo}")
//    public ResponseEntity<List<FileEntity>> getFilesByNoticeNo(@PathVariable int noticeNo) {
//        List<FileEntity> files = fileService.getFilesByNoticeNo(noticeNo);
//        return ResponseEntity.ok(files);
//    }
//
//    // 모든 파일 조회
//    @GetMapping
//    public ResponseEntity<List<FileEntity>> getAllFiles() {
//        List<FileEntity> files = fileService.getAllFiles();
//        return ResponseEntity.ok(files);
//    }
//
//    // 파일 삭제
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteFile(@PathVariable int id) {
//        fileService.deleteFile(id);
//        return ResponseEntity.noContent().build();
//    }
//}
