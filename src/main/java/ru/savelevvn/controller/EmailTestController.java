package ru.savelevvn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Email Testing", description = "Endpoints for testing email functionality")
public class EmailTestController {
    private final EmailService emailService;

    @Operation(
            summary = "Send test emails",
            description = "Sends verification and password reset test emails to the specified address"
    )
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