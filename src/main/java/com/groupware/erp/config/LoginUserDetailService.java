package com.groupware.erp.config;

import com.groupware.erp.member.entity.MemberEntity;
import com.groupware.erp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    // 로그인 처리
    @Override
    public UserDetails loadUserByUsername(String memberEmail) throws UsernameNotFoundException {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new UsernameNotFoundException("없는 회원 입니다..."));

        return User.builder().username(memberEntity.getMemberName())
                   .password(memberEntity.getMemberPassword())
                   .roles(memberEntity.getRole().name())
                   .build();
    }
}
