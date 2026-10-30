package com.myproject.task_management.serviceImpl;

import com.myproject.task_management.entity.RefreshToken;
import com.myproject.task_management.entity.User;
import com.myproject.task_management.exception.TokenReuseException;
import com.myproject.task_management.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public RefreshToken createRefreshToken(User user) {

        RefreshToken token = new RefreshToken();
        token.setUser(user);
        token.setToken(UUID.randomUUID().toString());
        token.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        token.setRevoked(false);

        return refreshTokenRepository.save(token);
    }

    public RefreshToken verifyExpiration(String token) {

        //we can use cache here to reduce database calls, but for simplicity we will query the database every time
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token RefreshTokenService.createRefreshToken() method"));

        if (refreshToken.isRevoked() ||
                refreshToken.getExpiryDate().isBefore(Instant.now())) {
            throw new TokenReuseException("Refresh token expired or revoked");
        }

        return refreshToken;
    }

    public void revokeToken(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).get();

        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }

}

