package com.groupware.erp.mail;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailForm {
    private String receiverMailAddress;

    @NotEmpty(message = "제목은 필수 사항입니다.")
    @Size(max = 200)
    private String subject;

    @NotEmpty(message = "내용을 입력하세요.")
    private String message;

    public MailForm(){}

    public MailForm(String receiverMailAddress, String subject, String message) {
        this.receiverMailAddress = receiverMailAddress;
        this.subject = subject;
        this.message = message;
    }
}
