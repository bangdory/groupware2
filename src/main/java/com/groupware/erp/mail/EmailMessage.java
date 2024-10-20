package com.groupware.erp.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {
    private String receiverMailAddress;
    private String subject;
    private String message;
}
