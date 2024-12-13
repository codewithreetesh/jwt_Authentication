package com.spring.jwt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.spring.jwt.services.CustomUserDetailService;

@Configuration
public class MySecurityConfig {
	
	@Autowired
	@Lazy
	private JwtAuthenticationFilter jwtfilter;
	
    @Autowired
    @Lazy 
    private CustomUserDetailService customUserDetailService;

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;
    
   
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // Disable CSRF as it's generally not needed for stateless APIs
            .authorizeHttpRequests() // Use this for request authorization
            .requestMatchers("/token").permitAll() // Allow public access to the token endpoint
            .anyRequest().authenticated() // Secure all other endpoints
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless session management
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(entryPoint); // Set the custom authentication entry point

        // Add JWT filter before the UsernamePasswordAuthenticationFilter
        http.addFilterBefore(jwtfilter, UsernamePasswordAuthenticationFilter.class);

        return http.build(); // Build and return the security filter chain
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = 
                http.getSharedObject(AuthenticationManagerBuilder.class);

        authBuilder.userDetailsService(customUserDetailService)
                   .passwordEncoder(passwordEncoder());

        return authBuilder.build();
    }
}
