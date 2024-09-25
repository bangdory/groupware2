package com.groupware.erp.admin.member.entity;

import com.groupware.erp.admin.member.dto.AdminMemberDetailDTO;
import com.groupware.erp.domain.member.Role;
import com.groupware.erp.member.dto.MemberJoinDTO;
import com.groupware.erp.member.entity.MemberEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity // JPA를 사용하려면 Entity Class가 팔수적이다.
@Getter
@Setter
@Table(name="member")	// DB table 이름
public class AdminMemberEntity {

    @Id // PK컬럼
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment와 같은 역할
    @Column(name = "member_no") // DB 컬럼 이름
    private Long memberNo;

    @Column(name = "member_email", length = 50, unique = true)  // varchar(50)에 unipue 속성
    private String memberEmail;

    @Column(name = "member_password")
    private String memberPassword;

    @Column(name = "member_name") // 아무 지정을 안해주면 varchar(255)
    private String memberName;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_role", nullable = false)
    private Role role;

    public static AdminMemberEntity toUpdateMember(AdminMemberDetailDTO adminMemberDetailDTO, PasswordEncoder passwordEncoder) {
        AdminMemberEntity adminMemberEntity = new AdminMemberEntity();

        adminMemberEntity.setMemberEmail(adminMemberDetailDTO.getMemberEmail());
        adminMemberEntity.setMemberPassword(passwordEncoder.encode(adminMemberDetailDTO.getMemberPassword()));
        adminMemberEntity.setMemberName(adminMemberDetailDTO.getMemberName());

        // 변환이 완료된 memberEntity 객체를 넘겨줌
        return adminMemberEntity;
    }
}
