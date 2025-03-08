package com.commerce.email_service.model;

import lombok.Data;

@Data
public class EmailDTO {
    private String to;
    private String subject;
    private String message;
}
