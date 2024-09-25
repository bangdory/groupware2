package com.groupware.erp.member.service.impl;

import com.groupware.erp.member.dto.MemberJoinDTO;
import com.groupware.erp.member.dto.MemberMapperDTO;
import com.groupware.erp.member.entity.MemberEntity;
import com.groupware.erp.member.repository.MemberMapperRepository;
import com.groupware.erp.member.repository.MemberRepository;
import com.groupware.erp.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    // Repository 생성자 주입
    private final MemberRepository memberRepository;
    private final MemberMapperRepository memberMapperRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입 처리
    @Override
    public Long join(MemberJoinDTO memberJoinDTO) {
        // JPA Repository는 무조건 Entity 타입만 받기 때문에 Entity 타입으로 변경
        MemberEntity memberEntity = MemberEntity.joinMember(memberJoinDTO, passwordEncoder);
        return memberRepository.save(memberEntity).getMemberNo();
    }

    // 회원목록 조회
    @Override
    public List<MemberMapperDTO> memberList(MemberMapperDTO memberMapperDTO) {
        return memberMapperRepository.memberList(memberMapperDTO);
    }
}
