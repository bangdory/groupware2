package com.groupware.erp.admin.member.dto;

import com.groupware.erp.admin.member.entity.AdminMemberEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminMemberDetailDTO {

    private Long memberNo;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public static AdminMemberDetailDTO toAdminMemberDetailDTO(AdminMemberEntity adminMemberEntity) {
        AdminMemberDetailDTO memberDetailDTO = new AdminMemberDetailDTO();
        memberDetailDTO.setMemberNo(adminMemberEntity.getMemberNo());
        memberDetailDTO.setMemberEmail(adminMemberEntity.getMemberEmail());
        memberDetailDTO.setMemberPassword(adminMemberEntity.getMemberPassword());
        memberDetailDTO.setMemberName(adminMemberEntity.getMemberName());
        return memberDetailDTO;
    }

    public static List<AdminMemberDetailDTO> change(List<AdminMemberEntity> memberEntityList) {
        List<AdminMemberDetailDTO> memberDetailDTOList = new ArrayList<>();
        for (AdminMemberEntity adminMemberEntity: memberEntityList) {
            memberDetailDTOList.add(toAdminMemberDetailDTO(adminMemberEntity));
        }
        return memberDetailDTOList;
    }
}
