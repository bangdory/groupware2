package com.groupware.erp.file.service;

import com.groupware.erp.file.entity.FileEntity;
import com.groupware.erp.file.repository.FileRepository;
import com.groupware.erp.notice.entity.CommunityNoticeEntity;
import com.groupware.erp.notice.service.CommunityNoticeService;
import com.groupware.erp.post.entity.CommunityPostEntity;
import com.groupware.erp.post.service.CommunityPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final String uploadDir = "src/main/resources/static/uploads/"; // 파일이 저장될 경로 설정

    @Autowired
    private FileRepository fileRepository;
    @Autowired
    private CommunityPostService communityPostService;
    @Autowired
    @Lazy
    private CommunityNoticeService communityNoticeService;

    public FileEntity saveFile(MultipartFile file, int postNo, int noticeNo) throws IOException {
        // 파일 저장
        String originalFilename = file.getOriginalFilename();
        String fileName = System.currentTimeMillis() + "_" + originalFilename; // 중복 방지를 위해 타임스탬프 추가
        Path filePath = Paths.get(uploadDir + fileName);
        Files.createDirectories(filePath.getParent()); // 디렉토리가 없을 경우 생성
        Files.write(filePath, file.getBytes());

        // DB에 파일 메타데이터 저장
        FileEntity fileEntity = new FileEntity();
        fileEntity.setOriginalFilename(originalFilename);
        fileEntity.setFileName(fileName);
        fileEntity.setFileSize(String.valueOf(file.getSize()));

        // postNo 또는 noticeNo가 존재하는 경우에만 설정
        if (postNo > 0) {
            CommunityPostEntity post = new CommunityPostEntity();
            post.setPostNo(postNo);
            fileEntity.setPost(post);
        }
        if (noticeNo > 0) {
            CommunityNoticeEntity notice = new CommunityNoticeEntity();
            notice.setNoticeNo(noticeNo);
            fileEntity.setNotice(notice);
        }

        return fileRepository.save(fileEntity);
    }

    public Optional<FileEntity> getFileById(int id) {
        return fileRepository.findById(id);
    }

    public List<FileEntity> getFilesByPostNo(int postNo) {
        CommunityPostEntity post = communityPostService.getPostById(postNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postNo"));
        return fileRepository.findByPost(post);
    }

    public List<FileEntity> getFilesByNoticeNo(int noticeNo) {
        CommunityNoticeEntity notice = communityNoticeService.getNoticeById(noticeNo)
                .orElseThrow(() -> new IllegalArgumentException("Invalid postNo"));
        return fileRepository.findByNotice(notice);
    }

    public List<FileEntity> getAllFiles() {
        return fileRepository.findAll();
    }

    public void deleteByNotice_NoticeNo(int noticeNo){
        fileRepository.deleteByNotice_NoticeNo(noticeNo);
    }

    public void deleteFile(int id) {
        Optional<FileEntity> fileEntityOptional = fileRepository.findById(id);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            File file = new File(uploadDir + fileEntity.getFileName());
            if (file.exists()) {
                file.delete(); // 파일 삭제
            }
            fileRepository.deleteById(id); // DB 데이터 삭제
        }
    }
}
