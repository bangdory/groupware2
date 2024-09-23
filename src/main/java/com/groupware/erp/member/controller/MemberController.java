package com.groupware.erp.member.controller;

import com.groupware.erp.member.dto.MemberJoinDTO;
import com.groupware.erp.member.dto.MemberMapperDTO;
import com.groupware.erp.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping(value = "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // 회원가입Page
    @GetMapping("/join")
    public String joinForm() {
        return "/member/join";
    }

    // 회원가입 처리
    @PostMapping("/memberJoin")
    public String memberJoin(@ModelAttribute MemberJoinDTO memberJoinDTO) {
        Long memberNo = memberService.join(memberJoinDTO);
        return "/member/login";
    }

    // 로그인Page
    @GetMapping("/login")
    public String loginForm() {
        return "/member/login";
    }

    // 회원목록Page
    @GetMapping("/list")
    public String listForm(Model model) {
        MemberMapperDTO paramMemberMapperDTO = new MemberMapperDTO();
        paramMemberMapperDTO.setMemberEmail("email.com");
        paramMemberMapperDTO.setMemberName("peter");

        List<MemberMapperDTO> memberList = memberService.memberList(paramMemberMapperDTO);
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }
}
