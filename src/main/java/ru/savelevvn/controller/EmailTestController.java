package ru.savelevvn.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.savelevvn.model.User;
import ru.savelevvn.service.EmailService;

@RestController
@RequestMapping("/api/test/email")
@RequiredArgsConstructor
public class EmailTestController {
    private final EmailService emailService;

    @GetMapping("/send-test")
    public String sendTestEmail(@RequestParam String email) {
        User testUser = new User();
        testUser.setEmail(email);
        testUser.setUsername("Test User");

        emailService.sendEmail(email, "Test Email", "This is a test email");
        emailService.sendVerificationEmail(testUser, "test-verification-token");
        emailService.sendPasswordResetEmail(testUser, "test-reset-token");

        return "Test emails sent to " + email;
    }
}