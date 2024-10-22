package com.groupware.erp.file.repository;

import com.groupware.erp.file.entity.FileEntity;
import com.groupware.erp.notice.entity.CommunityNoticeEntity;
import com.groupware.erp.post.entity.CommunityPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Integer> {
    // 공지사항 번호에 해당하는 파일 삭제
    void deleteByNotice_NoticeNo(int noticeNo);

    // 게시글 번호에 해당하는 파일 삭제
    void deleteByPost_PostNo(int postNo);

    List<FileEntity> findByPost(CommunityPostEntity post);
    List<FileEntity> findByNotice(CommunityNoticeEntity notice);
}
