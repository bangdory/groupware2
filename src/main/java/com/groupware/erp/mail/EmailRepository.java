package com.groupware.erp.mail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface EmailRepository extends JpaRepository<Mail, Integer> {
    List<Mail> findByReceiverMailAddress(String receiverMailAddress);

    List<Mail> findBySubject(String subject);

    List<Mail> findByCreateDate (Date createDate);

    Page<Mail> findAll(Specification<Mail> spec, Pageable pageable);
}
