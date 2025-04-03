package ru.savelevvn.service;

import ru.savelevvn.model.User;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
    void sendVerificationEmail(User user, String verificationToken);
    void sendPasswordResetEmail(User user, String resetToken);
}