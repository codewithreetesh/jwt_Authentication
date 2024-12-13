package com.spring.jwt.model;

import org.springframework.data.annotation.Id;

public class JwtRequest {

    @Id  // Mark the username as the primary key (or another field)
    private String username;
    
    private String password;

    // Default constructor
    public JwtRequest() {
        super();
    }

    // Parameterized constructor
    public JwtRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter and Setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString method
    @Override
    public String toString() {
        return "JwtRequest [username=" + username + ", password=" + password + "]";
    }
}
