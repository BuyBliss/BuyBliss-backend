package com.commerce.email_service.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EmailDTO {
    private String to;
    private String subject;
    private String message;

    @JsonCreator
    public EmailDTO(@JsonProperty("to") String to,
                   @JsonProperty("subject") String subject,
                   @JsonProperty("message") String message) {
        this.to = to;
        this.subject = subject;
        this.message = message;
    }
}
