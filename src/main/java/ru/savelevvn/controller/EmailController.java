package ru.savelevvn.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savelevvn.model.User;
import ru.savelevvn.service.EmailService;
import ru.savelevvn.service.JwtService;
import ru.savelevvn.service.UserService;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
@Tag(name = "Email", description = "Endpoints for email operations")
public class EmailController {

    private final EmailService emailService;
    private final UserService userService;
    private final JwtService jwtService; // Добавляем JwtService

    @Operation(
            summary = "Resend verification email",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Email sent successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping("/resend-verification")
    public ResponseEntity<Void> resendVerificationEmail(
            @RequestParam String email) {
        User user = userService.findByEmail(email);
        emailService.sendVerificationEmail(user, jwtService.generateEmailVerificationToken(user));
        return ResponseEntity.ok().build();
    }

    @Operation(
            summary = "Request password reset",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Reset email sent successfully"),
                    @ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping("/request-password-reset")
    public ResponseEntity<Void> requestPasswordReset(
            @RequestParam String email) {
        User user = userService.findByEmail(email);
        emailService.sendPasswordResetEmail(user, jwtService.generatePasswordResetToken(user));
        return ResponseEntity.ok().build();
    }
}