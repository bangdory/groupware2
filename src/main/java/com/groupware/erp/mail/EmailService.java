package com.groupware.erp.mail;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;


@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final EmailRepository emailRepository;

    public void sendMail(MailForm mailForm) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setFrom("klarnuri@naver.com");
            mimeMessageHelper.setTo(mailForm.getReceiverMailAddress()); // 수신자 메일
            mimeMessageHelper.setSubject(mailForm.getSubject()); // 메일 제목
            mimeMessageHelper.setText(mailForm.getMessage(), false); // 본문, HTML 여부
//            mimeMessageHelper.setText(mailForm.getMessage(), "html"); // 본문, HTML 여부
            javaMailSender.send(mimeMessage);
            Mail mail = new Mail();
            mail.setReceiverMailAddress(mailForm.getReceiverMailAddress());
            mail.setSubject(mailForm.getSubject());
            mail.setMessage(mailForm.getMessage());
            mail.setCreateDate(LocalDateTime.now());
            emailRepository.save(mail);
            log.info("메일 전송 성공");
        } catch (MessagingException e) {
            log.info("메일 전송 실패");
            throw new RuntimeException(e);
        }
    }

    // 페이징 처리 라이브러리 사용해보기
    public Page<Mail> getList(int page, String kw) {
        // 정렬 순서 변경 DESC
        ArrayList<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("createDate")); // createDate 프로퍼티를 찾아 desc 정렬
        /**
         * params : (조회할 페이지, 페이지당 Request 객체수)
         */
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts)); // PageRequest 가 Pageable 을 상속함
        Specification<Mail> spec = search(kw);
        return emailRepository.findAll(spec, pageable);
//        return questionRepository.findAllByKeyword(kw, pageable);
    }

    private Specification<Mail> search(String kw) {
        return new Specification<>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Mail> m, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                query.distinct(true); // 중복 제거
                return criteriaBuilder.or(
                        criteriaBuilder.like(m.get("subject"), "%" + kw + "%"), // 제목
                        criteriaBuilder.like(m.get("message"), "%" + kw + "%"), // 내용
                        criteriaBuilder.like(m.get("receiverMailAddress"), "%" + kw + "%") // 작성자
                );
            }
        };
    }

    public Mail getMail(Integer id) {
        Optional<Mail> mail = emailRepository.findById(id);
        if (mail.isPresent()) {
            return mail.get();
        } else {
            throw new EmailNotFoundException("mail not found");
        }
    }

    public void delete(Mail mail) {
        emailRepository.delete(mail);
    }
}
