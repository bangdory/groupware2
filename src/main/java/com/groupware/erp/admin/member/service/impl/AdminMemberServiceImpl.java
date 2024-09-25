package com.groupware.erp.admin.member.service.impl;

import com.groupware.erp.admin.member.dto.AdminMemberDetailDTO;
import com.groupware.erp.admin.member.entity.AdminMemberEntity;
import com.groupware.erp.admin.member.repository.AdminMemberRepository;
import com.groupware.erp.admin.member.service.AdminMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminMemberServiceImpl implements AdminMemberService {

    private final AdminMemberRepository adminMemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AdminMemberDetailDTO> findAll() {
        List<AdminMemberEntity> memberEntityList = adminMemberRepository.findAll();
        List<AdminMemberDetailDTO> memberDetailDTOList = AdminMemberDetailDTO.change(memberEntityList);
        return memberDetailDTOList;
    }

    @Override
    public AdminMemberDetailDTO findByNo(Long memberNo) {
        return AdminMemberDetailDTO.toAdminMemberDetailDTO(adminMemberRepository.findById(memberNo).get());
    }

    @Override
    public Long update(AdminMemberDetailDTO adminMemberDetailDTO) {
        AdminMemberEntity adminMemberEntity = AdminMemberEntity.toUpdateMember(adminMemberDetailDTO, passwordEncoder);
        return adminMemberRepository.save(adminMemberEntity).getMemberNo();
    }

    @Override
    public void deleteByNo(Long memberNo) {
        adminMemberRepository.deleteById(memberNo);
    }
}
