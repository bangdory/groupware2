package com.groupware.erp.file.entity;

import com.groupware.erp.notice.entity.CommunityNoticeEntity;
import com.groupware.erp.post.entity.CommunityPostEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "file")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no")
    private int fileNo;

    @ManyToOne
    @JoinColumn(name = "post_no")
    private CommunityPostEntity post;

    @ManyToOne
    @JoinColumn(name = "notice_no")
    private CommunityNoticeEntity notice;

    @Column(name = "original_filename", length = 100)
    private String originalFilename;

    @Column(name = "file_name", length = 100)
    private String fileName;

    @Column(name = "file_size", length = 100)
    private String fileSize;
}