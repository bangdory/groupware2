package com.groupware.erp.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberMapperDTO {

    private Long memberNo;
    private String memberEmail;
    private String memberPassword;
    private String memberName;

    public MemberMapperDTO (String memberEmail, String memberPassword, String memberName) {
        this.memberEmail = memberEmail;
        this.memberPassword = memberPassword;
        this.memberName = memberName;
    }
}