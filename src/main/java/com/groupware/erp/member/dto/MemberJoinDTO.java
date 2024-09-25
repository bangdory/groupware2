package com.groupware.erp.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinDTO {

    private String memberEmail;
    private String memberPassword;
    private String memberName;
}