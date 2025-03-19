package ru.savelevvn.controller;

import org.springframework.web.bind.annotation.*;
import ru.savelevvn.dto.RegistrationRequest;
import ru.savelevvn.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/register")
    public String register(@RequestBody RegistrationRequest request) {
        authService.registerUser(request);
        return "User registered successfully";
    }

    @PostMapping("/users/{userId}/deactivate")
    public String deactivateUser(@PathVariable Long userId) {
        authService.deactivateUser(userId);
        return "User deactivated successfully";
    }
    @PostMapping("/users/{userId}/update-password")
    public String updatePassword(@PathVariable Long userId, @RequestBody String newPassword) {
        authService.updatePassword(userId, newPassword);
        return "Password updated successfully";
    }
}
