package ru.savelevvn.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.LOCKED)
public class AccountLockedException extends RuntimeException {
    public AccountLockedException() {
        super("Account is locked");
    }

    public AccountLockedException(String message) {
        super(message);
    }
}