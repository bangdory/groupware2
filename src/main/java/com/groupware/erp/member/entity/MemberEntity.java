package com.groupware.erp.member.entity;

import com.groupware.erp.domain.member.Role;
import com.groupware.erp.member.dto.MemberJoinDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity // JPA를 사용하려면 Entity class가 필수적
@Getter
@Setter
@Table(name="member")	// DB table 이름
public class MemberEntity {

    @Id // PK컬럼
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment와 같은 역할
    @Column(name = "member_no") // DB 컬럼 이름
    private Long memberNo;

    @Column(length = 50, unique = true)  // varchar(50)에 unique 속성
    private String memberEmail;

    @Column()
    private String memberPassword;

    @Column() // 아무 지정을 안해주면 varchar(255)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // MemberJoinDTO -> MemberEntity 객체로 변환
    public static MemberEntity joinMember(MemberJoinDTO memberjoinDTO, PasswordEncoder passwordEncoder) {
        MemberEntity memberEntity = new MemberEntity();

        memberEntity.setMemberEmail(memberjoinDTO.getMemberEmail());
        memberEntity.setMemberPassword(passwordEncoder.encode(memberjoinDTO.getMemberPassword()));
        memberEntity.setMemberName(memberjoinDTO.getMemberName());
        memberEntity.setRole(Role.USER); // 회원가입시 일반사용자로 등록, 필요시 차후 관리자화면에서 관리자로 등록

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return memberEntity;
    }
}