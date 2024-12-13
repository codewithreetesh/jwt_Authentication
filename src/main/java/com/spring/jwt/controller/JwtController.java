package com.spring.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.jwt.Helper.JwtUtil;
import com.spring.jwt.model.JwtRequest;
import com.spring.jwt.model.JwtResponse;
import com.spring.jwt.services.CustomUserDetailService;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    private final JwtUtil jwtUtil;

    @Autowired
    public JwtController(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println("JWT Request: " + jwtRequest);

        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            jwtRequest.getUsername(),
                            jwtRequest.getPassword()
                    )
            );
            System.out.println("Authentication successful: " + authentication);
        } catch (BadCredentialsException e) {
        	System.out.println("invalid username");
            
        	throw new Exception("Invalid username or password", e);
           }

        // Load user details from CustomUserDetailService
        UserDetails userDetails = customUserDetailService.loadUserByUsername(jwtRequest.getUsername());
        if (userDetails == null) {
            throw new Exception("User not found in database");
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(userDetails);
        System.out.println("Generated Token: " + token);

        // Return token in the response
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
