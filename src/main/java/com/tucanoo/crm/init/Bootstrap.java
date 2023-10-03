package com.tucanoo.crm.init;

import com.tucanoo.crm.data.entities.User;
import com.tucanoo.crm.data.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    void appReady(ApplicationReadyEvent event) {
        // Initialise initial users
        // Create admin user.
        if (! userRepository.findByUsername("admin").isPresent()) {
            User adminUser = User.builder()
                .username("admin")
                .fullName("Sheev Palpatine")
                .password(passwordEncoder.encode("admin"))
                .role("ROLE_ADMIN")
                .enabled(true)
                .build();

            userRepository.save(adminUser);
        }

        // Create standard user.
        if (! userRepository.findByUsername("user").isPresent()) {
            User adminUser = User.builder()
                .username("user")
                .fullName("Darth Tyranus")
                .password(passwordEncoder.encode("user"))
                .role("ROLE_USER")
                .enabled(true)
                .build();

            userRepository.save(adminUser);
        }

        // create readonly user
        if (! userRepository.findByUsername("readonly_user").isPresent()) {
            User adminUser = User.builder()
                .username("readonly_user")
                .fullName("Shin Hati")
                .password(passwordEncoder.encode("readonly_user"))
                .role("ROLE_READONLY_USER")
                .enabled(true)
                .build();

            userRepository.save(adminUser);
        }
    }
}
