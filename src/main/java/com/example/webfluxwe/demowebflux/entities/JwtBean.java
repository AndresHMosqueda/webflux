package com.example.webfluxwe.demowebflux.entities;


import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class JwtBean {

    private String expiresIn;

    private String issueAt;

    private String token;
    public LocalDateTime expiryTime;

    public JwtBean(){}

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getIssueAt() {
        return issueAt;
    }

    public void setIssueAt(String issueAt) {
        this.issueAt = issueAt;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }
    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    @Override
    public String toString() {
        return "JwtBean [expiresIn=" + expiresIn + ", issueAt=" + issueAt + ", token=" + token + "]";
    }

}
