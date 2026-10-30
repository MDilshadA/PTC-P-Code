package com.myproject.task_management.entity;

import jakarta.persistence.*;
import lombok.Data;

/**
 * The refresh token is used to obtain a new access token when the current access token expires, allowing users to stay authenticated without having to log in again.
 * It contains a unique token string, a reference to the associated user, and an expiry date.
 * This entity is stored in the "refresh_tokens" table in the database.
 * The token is associated with a specific user and has an expiration time to enhance security.
 */

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String token;

    @ManyToOne
    private User user;

    private Instant expiryDate;

    private boolean revoked;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
