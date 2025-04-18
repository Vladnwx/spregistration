package ru.savelevvn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class EmailSendingException extends RuntimeException {
    public EmailSendingException(String message) {
        super("Failed to send email: " + message);
    }

    public EmailSendingException(String recipient, Throwable cause) {
        super("Failed to send email to " + recipient, cause);
    }
}