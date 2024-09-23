package com.groupware.erp.admin.member.service;

import com.groupware.erp.admin.member.dto.AdminMemberDetailDTO;

import java.util.List;

public interface AdminMemberService {

    List<AdminMemberDetailDTO> findAll();
    AdminMemberDetailDTO findByNo(Long memberNo);
    Long update(AdminMemberDetailDTO adminMemberDetailDTO);
    void deleteByNo(Long memberNo);
}
