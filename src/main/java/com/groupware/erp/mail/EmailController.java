package com.groupware.erp.mail;

import com.groupware.erp.employee.entity.EmployeeEntity;
import com.groupware.erp.employee.repository.EmployeeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;
    private final EmployeeRepository employeeRepository;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
//    @RequestMapping(value = "/", method = org.springframework.web.bind.annotation.RequestMethod.GET)
    public String root() {
        return "redirect:/mail/list";
    }

    @GetMapping({"/send", "/send/{empNo}"})
//    @GetMapping("/send/{empNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public String sendEmail(MailForm mailForm, @PathVariable(value = "empNo", required = false) String empNo
            , Model model) {
//        model.addAttribute("mailForm", new MailForm());
        // 메일 받을 receiverMailAddress 가져오는 로직 추가할 것!!

        if (empNo != null) {
            Optional<EmployeeEntity> employee = employeeRepository.findByEmpNo(empNo);
            if (employee.isPresent()) {
                model.addAttribute("empData", employee.get());
                mailForm.setReceiverMailAddress(employee.get().getEmpEmail());
            } else {
                model.addAttribute("errorMessage", "해당 사원을 찾을 수 없습니다.");
            }
        }
        System.out.println("!!!!!!!!!!mailForm!!!! = " + mailForm.toString());
        return "email/send_mail";
    }

    /*@PostMapping("/send/{empNo}")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView sendMail(@Valid MailForm mailForm, BindingResult bindingResult, @PathVariable(value = "empNo", required = false) String empNo) {
        if (bindingResult.hasErrors()) {
            // 바인딩 에러 발생시
            ModelAndView modelAndView = new ModelAndView("email/send_mail"); // 뷰 이름
            modelAndView.setStatus(HttpStatus.BAD_REQUEST); // HTTP 상태 코드 설정
            modelAndView.addObject("errors", bindingResult.getAllErrors()); // 오류 메시지 추가
            return modelAndView;
        }*/
    @PostMapping({"/send", "/send/{empNo}"})
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView sendMail(@Valid MailForm mailForm, BindingResult bindingResult, @PathVariable(value = "empNo", required = false) String empNo, Model model) {
        // empNo로 직원 정보 가져오기
        if (bindingResult.hasErrors()) {
            if (empNo != null) {
                Optional<EmployeeEntity> employee = employeeRepository.findByEmpNo(empNo);
                // 바인딩 에러 발생시
                if (employee.isPresent()) {
                    model.addAttribute("empData", employee.get()); // empData 추가
                } else {
                    model.addAttribute("errorMessage", "해당 사원을 찾을 수 없습니다.");
                }
                // 에러가 발생했을 때 현재 모델을 반환
                return new ModelAndView("email/send_mail", model.asMap());
            }
        }

        // 수신자 이메일, 제목, 내용이 비어있는지 체크
        if (mailForm.getReceiverMailAddress() == null || mailForm.getReceiverMailAddress().isEmpty() ||
                mailForm.getSubject() == null || mailForm.getSubject().isEmpty() ||
                mailForm.getMessage() == null || mailForm.getMessage().isEmpty()) {

            bindingResult.reject("mailForm.empty", "메일 수신자, 제목 및 내용을 모두 입력해야 합니다."); // 에러 메시지 추가

            // 에러가 발생했을 때 현재 모델을 반환
            return new ModelAndView("email/send_mail", model.asMap());
        }

        mailForm = MailForm.builder()
//                .receiverMailAddress("klarnuri@gmail.com") // 수신자 메일
                .receiverMailAddress(mailForm.getReceiverMailAddress()) // 수신자 메일
                .subject(mailForm.getSubject()) // 메일 제목
                .message(mailForm.getMessage()) // 메일 본문
                .build();
        emailService.sendMail(mailForm);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.OK); // HTTP 상태 코드 설정
        modelAndView.addObject("message", "메일이 성공적으로 전송되었습니다."); // 성공 메시지 추가

        return new ModelAndView("redirect:/mail/list");
    }

    /*@PostMapping("/send")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView sendMail(@Valid MailForm mailForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 바인딩 에러 발생시
            ModelAndView modelAndView = new ModelAndView("email/send_mail"); // 뷰 이름
            modelAndView.setStatus(HttpStatus.BAD_REQUEST); // HTTP 상태 코드 설정
            modelAndView.addObject("errors", bindingResult.getAllErrors()); // 오류 메시지 추가
            return modelAndView;
        }

//        System.out.println("mailForm.getSubject() = " + mailForm.getSubject());
//        System.out.println("mailForm.getMessage() = " + mailForm.getMessage());

        mailForm = MailForm.builder()
//                .receiverMailAddress("klarnuri@gmail.com") // 수신자 메일
                .receiverMailAddress(mailForm.getReceiverMailAddress()) // 수신자 메일
                .subject(mailForm.getSubject()) // 메일 제목
                .message(mailForm.getMessage()) // 메일 본문
                .build();
        emailService.sendMail(mailForm);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.OK); // HTTP 상태 코드 설정
        modelAndView.addObject("message", "메일이 성공적으로 전송되었습니다."); // 성공 메시지 추가

        return new ModelAndView("redirect:/mail/list");
    }
*/
    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String list(Model model,
                       @RequestParam(value = "page", defaultValue = "0", required = false) int page,
                       // defaultValue 꼭 넣을것!!!!!
                       @RequestParam(value = "kw", defaultValue = "", required = false) String kw) {
        Page<Mail> paging = emailService.getList(page, kw);
        System.out.println("paging = " + paging + " page = " + page + " keyword = " + kw);
        model.addAttribute("paging", paging);
        model.addAttribute("kw", kw);
        return "email/mail_list";
    }

    @GetMapping("/detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    // mail_detail 템플릿 Form 태그에 th:object="${mailForm}" 를 사용 -> MailForm 이 필요함 (mailForm 은 변수 이름 바인딩)
    // 디테일 메일의 수신자 객체 받아올 것!!
    public String detail(Model model, @PathVariable("id") Integer id,
                         MailForm mailForm) {
        Mail mail = emailService.getMail(id);
        model.addAttribute("mail", mail);
        return "email/mail_detail";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String mailDelete(Principal principal, @PathVariable("id") Integer id) {
        Mail mail = emailService.getMail(id);
/*
        if (!mail.getReceiverMailAddress().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제 권한이 없습니다.");
        }
*/
        emailService.delete(mail);
        return "redirect:/mail";
    }
}