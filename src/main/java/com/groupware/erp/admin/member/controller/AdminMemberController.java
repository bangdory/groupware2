package com.groupware.erp.admin.member.controller;

import com.groupware.erp.admin.member.dto.AdminMemberDetailDTO;
import com.groupware.erp.admin.member.service.AdminMemberService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller()
@RequestMapping(value = "/admin/member")
public class AdminMemberController {

    @Autowired
    private AdminMemberService adminMemberService;

    // 회원목록Page
    @GetMapping("/list")
    public String listForm(Model model) {
        List<AdminMemberDetailDTO> memberList = adminMemberService.findAll();
        model.addAttribute("memberList", memberList);
        return "/member/list";
    }

    // 회원상세Page
    @GetMapping("/detail/{memberNo}")
    public String findById(@PathVariable Long memberNo, Model model) {
        AdminMemberDetailDTO adminMemberDetailDTO = adminMemberService.findByNo(memberNo);
        model.addAttribute("member", adminMemberDetailDTO);
        return "/admin/member/detail";
    }

    // 회원수정Page
    @GetMapping("/update/{memberNo}")
    public String updateForm(@PathVariable Long memberNo, Model model, HttpSession session) {
        AdminMemberDetailDTO adminMemberDetailDTO = adminMemberService.findByNo(memberNo);
        model.addAttribute("member", adminMemberDetailDTO);
        return "/admin/member/update";
    }

    // 회원수정
    @PutMapping("/updateMember/{memberNo}")
    public ResponseEntity memberUpdate(@RequestBody AdminMemberDetailDTO adminMemberDetailDTO) {
        Long memberNo = adminMemberService.update(adminMemberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 회원삭제
    @PostMapping("/delete/{memberNo}")
    public ResponseEntity deleteById(@PathVariable("memberNo") Long memberNo) {
        adminMemberService.deleteByNo(memberNo);
        return new ResponseEntity(HttpStatus.OK);
    }
}
