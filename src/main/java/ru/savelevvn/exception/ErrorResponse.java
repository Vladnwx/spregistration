package ru.savelevvn.exception;

public record ErrorResponse(
        String message,
        String errorCode
) {}