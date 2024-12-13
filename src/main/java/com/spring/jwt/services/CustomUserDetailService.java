package com.spring.jwt.services;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    // Use constructor injection to avoid circular dependency
    public CustomUserDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Simulated user check (replace with actual database check)
        if (username.equalsIgnoreCase("Durgesh")) {
            return User.builder()
                    .username("Durgesh")
                    .password(passwordEncoder.encode("durgesh123")) // Use the encoded password
                    .authorities(Collections.emptyList()) // No roles or permissions
                    .build();
        }
        System.out.println("username not found");
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
