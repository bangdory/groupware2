package com.groupware.erp.notice.service;

import com.groupware.erp.file.service.FileService;
import com.groupware.erp.notice.entity.CommunityNoticeEntity;
import com.groupware.erp.notice.repository.CommunityNoticeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommunityNoticeService {

    @Autowired
    private CommunityNoticeRepository communityNoticeRepository;

    @Autowired
    private FileService fileService; // 파일 삭제를 위해 FileRepository 주입

    // 공지사항 저장
    public CommunityNoticeEntity saveNotice(CommunityNoticeEntity notice) {
        return communityNoticeRepository.save(notice);
    }

    // ID로 공지사항 조회
    public Optional<CommunityNoticeEntity> getNoticeById(int id) {
        return communityNoticeRepository.findById(id);
    }

    // 모든 공지사항 조회
    public List<CommunityNoticeEntity> getAllNotices() {
        return communityNoticeRepository.findAll();
    }

    // 공지사항 삭제 (공지사항과 연관된 파일도 함께 삭제)
    @Transactional
    public void deleteNotice(int id) {
        // 공지사항 삭제 전에 해당 공지사항과 연관된 파일 삭제
        fileService.deleteByNotice_NoticeNo(id);

        // 공지사항 삭제
        communityNoticeRepository.deleteById(id);
    }

    // 공지사항 업데이트
    public CommunityNoticeEntity updateNotice(int id, CommunityNoticeEntity updatedNotice) {
        return communityNoticeRepository.findById(id).map(existingNotice -> {
            existingNotice.setNoticeTitle(updatedNotice.getNoticeTitle());
            existingNotice.setNoticeContent(updatedNotice.getNoticeContent());
            existingNotice.setNoticeDate(updatedNotice.getNoticeDate());
            existingNotice.setNoticeView(updatedNotice.getNoticeView());
            existingNotice.setNoticeState(updatedNotice.getNoticeState());
            return communityNoticeRepository.save(existingNotice);
        }).orElseThrow(() -> new RuntimeException("Notice not found with id " + id));
    }
}
