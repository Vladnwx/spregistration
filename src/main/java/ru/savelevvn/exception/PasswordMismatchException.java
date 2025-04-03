package ru.savelevvn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordMismatchException extends RuntimeException {
    public PasswordMismatchException() {
        super("Current password is incorrect");
    }

    public PasswordMismatchException(String message) {
        super(message);
    }
}