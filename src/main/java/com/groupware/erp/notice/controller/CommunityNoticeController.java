package com.groupware.erp.notice.controller;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.service.impl.EmployeeServiceImpl;
import com.groupware.erp.notice.dto.CommunityNoticeDTO;
import com.groupware.erp.notice.entity.CommunityNoticeEntity;
import com.groupware.erp.notice.service.CommunityNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/notices")
public class CommunityNoticeController {
    @Autowired
    private CommunityNoticeService communityNoticeService;
    @Autowired
    private EmployeeServiceImpl employeeServiceImpl;

    @GetMapping("/list")
    public String post () {
        return "notice/notiList";
    }

    @GetMapping("/createPage")
    public String create () {
        return "notice/noticeCreate";
    }

    @GetMapping("/detailPage")
    public String detail () {
        return "notice/noticeDetail";
    }
    // 공지사항 생성
    @PostMapping
    public ResponseEntity<CommunityNoticeDTO> createNotice(@RequestBody Map<String, Object> noticeData) {
        String empNo = (String) noticeData.get("empNo");

        // empNo로 EmployeeEntity를 찾는 로직 추가
        EmployeeEntity employee = employeeServiceImpl.findEmployeeById(empNo);
        if (employee == null) {
            return ResponseEntity.badRequest().body(null);  // Employee를 찾을 수 없으면 오류 반환
        }

        // 새로운 공지사항 엔티티 생성
        CommunityNoticeEntity notice = new CommunityNoticeEntity();
        notice.setNoticeTitle((String) noticeData.get("noticeTitle"));
        notice.setNoticeContent((String) noticeData.get("noticeContent"));
        notice.setNoticeDate(LocalDate.now()); // 현재 날짜로 설정
        notice.setNoticeView(0); // 조회수 0으로 초기화
        notice.setEmployee(employee); // Employee 설정

        // 공지사항 저장
        CommunityNoticeEntity savedNotice = communityNoticeService.saveNotice(notice);

        // 엔티티를 DTO로 변환
        CommunityNoticeDTO noticeDTO = convertToDTO(savedNotice);

        return ResponseEntity.ok(noticeDTO);
    }

    // 공지사항 ID로 조회
    @GetMapping("/{id}")
    public ResponseEntity<CommunityNoticeDTO> getNoticeById(@PathVariable int id) {
        return communityNoticeService.getNoticeById(id)
                .map(notice -> ResponseEntity.ok(convertToDTO(notice)))
                .orElse(ResponseEntity.notFound().build());
    }

    // 모든 공지사항 조회
    @GetMapping
    public ResponseEntity<List<CommunityNoticeDTO>> getAllNotices() {
        List<CommunityNoticeEntity> notices = communityNoticeService.getAllNotices();

        // 엔티티 리스트를 DTO 리스트로 변환
        List<CommunityNoticeDTO> noticeDTOs = notices.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return ResponseEntity.ok(noticeDTOs);
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotice(@PathVariable int id) {
        communityNoticeService.deleteNotice(id);
        return ResponseEntity.noContent().build();
    }

    // 공지사항 수정
    @PutMapping("/{id}")
    public ResponseEntity<CommunityNoticeDTO> updateNotice(@PathVariable int id, @RequestBody CommunityNoticeDTO updatedNotice) {
        try {
            CommunityNoticeEntity noticeEntity = convertToEntity(updatedNotice);
            CommunityNoticeEntity updated = communityNoticeService.updateNotice(id, noticeEntity);

            // 엔티티를 DTO로 변환
            CommunityNoticeDTO updatedDTO = convertToDTO(updated);

            return ResponseEntity.ok(updatedDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 엔티티를 DTO로 변환하는 헬퍼 메소드
    private CommunityNoticeDTO convertToDTO(CommunityNoticeEntity entity) {
        CommunityNoticeDTO dto = new CommunityNoticeDTO();
        dto.setNoticeNo(entity.getNoticeNo());
        dto.setNoticeTitle(entity.getNoticeTitle());
        dto.setNoticeContent(entity.getNoticeContent());
        dto.setNoticeDate(entity.getNoticeDate());
        dto.setNoticeView(entity.getNoticeView());
        dto.setNoticeState(entity.getNoticeState());

        // 직원 정보 설정
        EmployeeEntity employee = entity.getEmployee();
        dto.setEmpNo(employee.getEmpNo());
        dto.setEmpName(employee.getEmpName());
        dto.setEmpEmail(employee.getEmpEmail());
        dto.setEmpPhone(employee.getEmpPhone());
//        dto.setEmpHireDate(employee.getEmpHireDate());
        dto.setDepartment(employee.getDepartment());
        dto.setEmpGrade(employee.getEmpGrade());

        return dto;
    }

    // DTO를 엔티티로 변환하는 헬퍼 메소드
    private CommunityNoticeEntity convertToEntity(CommunityNoticeDTO dto) {
        CommunityNoticeEntity entity = new CommunityNoticeEntity();
        entity.setNoticeNo(dto.getNoticeNo());
        entity.setNoticeTitle(dto.getNoticeTitle());
        entity.setNoticeContent(dto.getNoticeContent());
        entity.setNoticeDate(dto.getNoticeDate());
        entity.setNoticeView(dto.getNoticeView());
        entity.setNoticeState(dto.getNoticeState());

        // 직원 정보 설정 (필요에 따라 EmployeeEntity로 변환해야 함)
        EmployeeEntity employee = new EmployeeEntity();
        employee.setEmpNo(dto.getEmpNo());
        employee.setEmpName(dto.getEmpName());
        employee.setEmpEmail(dto.getEmpEmail());
        employee.setEmpPhone(dto.getEmpPhone());
//        employee.setEmpHireDate(dto.getEmpHireDate());
        employee.setDepartment(dto.getDepartment());
        employee.setEmpGrade(dto.getEmpGrade());

        entity.setEmployee(employee);

        return entity;
    }
}
//
//    @PostMapping
//    public ResponseEntity<CommunityNoticeEntity> createNotice(@RequestBody Map<String, Object> noticeData) {
//        String empNo = (String) noticeData.get("empNo");
//
//        // empNo로 EmployeeEntity를 찾는 로직 추가
//        EmployeeEntity employee = employeeServiceImpl.findEmployeeById(empNo);
//        if (employee == null) {
//            return ResponseEntity.badRequest().body(null);  // Employee를 찾을 수 없으면 오류 반환
//        }
//
//        // Create a new notice entity
//        CommunityNoticeEntity notice = new CommunityNoticeEntity();
//        notice.setNoticeTitle((String) noticeData.get("noticeTitle"));
//        notice.setNoticeContent((String) noticeData.get("noticeContent"));
//        notice.setNoticeDate(LocalDate.now()); // 현재 날짜로 설정
//        notice.setNoticeView(0); // 조회수 0으로 초기화
//        notice.setEmployee(employee); // Employee 설정
//
//        // 공지사항 저장
//        CommunityNoticeEntity savedNotice = communityNoticeService.saveNotice(notice);
//        return ResponseEntity.ok(savedNotice);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CommunityNoticeEntity> getNoticeById(@PathVariable int id) {
//        return communityNoticeService.getNoticeById(id)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @GetMapping
//    public ResponseEntity<List<CommunityNoticeEntity>> getAllNotices() {
//        return ResponseEntity.ok(communityNoticeService.getAllNotices());
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteNotice(@PathVariable int id) {
//        communityNoticeService.deleteNotice(id);
//        return ResponseEntity.noContent().build();
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CommunityNoticeEntity> updateNotice(@PathVariable int id, @RequestBody CommunityNoticeEntity updatedNotice) {
//        try {
//            CommunityNoticeEntity updated = communityNoticeService.updateNotice(id, updatedNotice);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
