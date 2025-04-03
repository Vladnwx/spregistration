package ru.savelevvn.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.savelevvn.model.Privilege;
import ru.savelevvn.model.Role;
import ru.savelevvn.model.User;
import ru.savelevvn.repository.PrivilegeRepository;
import ru.savelevvn.repository.RoleRepository;
import ru.savelevvn.repository.UserRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.username}")
    private String adminUsername;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.email}")
    private String adminEmail;



    @Override
    public void run(String... args) {
        if (userRepository.findByUsername(adminUsername).isEmpty()) {
            // Создаем базовые привилегии
            Privilege adminPrivilege = privilegeRepository.save(
                    Privilege.builder()
                            .name("ADMIN_PRIVILEGE")
                            .description("Full administrative access")
                            .build()
            );

            // Создаем роль администратора
            Role adminRole = roleRepository.save(
                    Role.builder()
                            .name("ROLE_ADMIN")
                            .description("Administrator role")
                            .build()
            );
            adminRole.addPrivilege(adminPrivilege);
            roleRepository.save(adminRole);

            // Создаем пользователя администратора
            User admin = User.builder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode(adminPassword))
                    .email(adminEmail)
                    .enabled(true)
                    .build();
            admin.addRole(adminRole);
            userRepository.save(admin);

            System.out.printf("Создан администратор: %s / %s%n", adminUsername, adminPassword);
        }
    }
}