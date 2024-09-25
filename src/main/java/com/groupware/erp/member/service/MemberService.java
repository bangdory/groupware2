package com.groupware.erp.member.service;

import com.groupware.erp.member.dto.MemberJoinDTO;
import com.groupware.erp.member.dto.MemberMapperDTO;

import java.util.List;

public interface MemberService {

    // 회원가입 처리
    Long join(MemberJoinDTO memberJoinDTO);

    // 회원목록 조회
    List<MemberMapperDTO> memberList(MemberMapperDTO memberMapperDTO);
}
