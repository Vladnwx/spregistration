package ru.savelevvn.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.savelevvn.model.User;
import ru.savelevvn.service.EmailService;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Value("${app.email.from}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

    @Override
    public void sendVerificationEmail(User user, String token) {
        String verificationUrl = baseUrl + "/api/auth/verify-email?token=" + token;
        String subject = "Подтверждение email";
        String text = String.format(
                "Здравствуйте, %s!\n\n" +
                        "Для завершения регистрации перейдите по ссылке:\n%s\n\n" +
                        "Ссылка действительна 24 часа.",
                user.getUsername(), verificationUrl
        );

        sendEmail(user.getEmail(), subject, text);
    }

    @Override
    public void sendPasswordResetEmail(User user, String token) {
        String resetUrl = baseUrl + "/api/auth/reset-password?token=" + token;
        String subject = "Сброс пароля";
        String text = String.format(
                "Здравствуйте, %s!\n\n" +
                        "Для сброса пароля перейдите по ссылке:\n%s\n\n" +
                        "Ссылка действительна 1 час.",
                user.getUsername(), resetUrl
        );

        sendEmail(user.getEmail(), subject, text);
    }
}