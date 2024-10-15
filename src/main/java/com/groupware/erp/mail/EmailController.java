package com.groupware.erp.mail;

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

@Controller
@RequiredArgsConstructor
@RequestMapping("/mail")
public class EmailController {

    private final EmailService emailService;

    @GetMapping("")
    @PreAuthorize("hasRole('ADMIN')")
//    @RequestMapping(value = "/", method = org.springframework.web.bind.annotation.RequestMethod.GET)
    public String root() {
        return "redirect:/mail/list";
    }

    @GetMapping("/send")
    @PreAuthorize("hasRole('ADMIN')")
    public String sendEmail(MailForm mailForm) {
//        model.addAttribute("mailForm", new MailForm());
        // 메일 받을 receiverMailAddress 가져오는 로직 추가할 것!!
        return "email/send_mail";
    }

    @PostMapping("/send")
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
                .receiverMailAddress("klarnuri@gmail.com") // 수신자 메일
                .subject(mailForm.getSubject()) // 메일 제목
                .message(mailForm.getMessage()) // 메일 본문
                .build();
        emailService.sendMail(mailForm);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setStatus(HttpStatus.OK); // HTTP 상태 코드 설정
        modelAndView.addObject("message", "메일이 성공적으로 전송되었습니다."); // 성공 메시지 추가

        return new ModelAndView("redirect:/mail/list");
    }

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
    public String detail(Model model, @PathVariable("id") Integer id,
                         MailForm mailForm) {
        Mail mail = emailService.getMail(id);
//        System.out.println("여기까진 작동됨!!!" + mail.getMessage());
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
        return "redirect:/";
    }
}