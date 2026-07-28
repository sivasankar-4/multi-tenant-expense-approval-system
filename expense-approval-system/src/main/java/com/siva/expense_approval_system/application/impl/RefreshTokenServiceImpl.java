package com.siva.expense_approval_system.application.impl;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.siva.expense_approval_system.application.service.RefreshTokenService;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

        private final RefreshTokenRepository refreshTokenRepository;

        public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository) {
            this.refreshTokenRepository = refreshTokenRepository;
        }

    @Override
    public String createRefreshToken(User user) {

        String refreshToken = UUID.randomUUID().toString();

        RefreshToken token = new RefreshToken();

        token.setUser(user);
        token.setTokenHash(hashToken(refreshToken));
        token.setExpiresAt(LocalDateTime.now().plusDays(7));
        token.setRevoked(false);
        token.setCreatedAt(LocalDateTime.now());

        refreshTokenRepository.save(token);

        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {

        String tokenHash = hashToken(token);

        return refreshTokenRepository.findByTokenHash(tokenHash);
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken refreshToken) {

        if (refreshToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token has expired");
        }

        if (Boolean.TRUE.equals(refreshToken.getRevoked())) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        return refreshToken;
    }

    @Override
    public void deleteByUser(User user) {
        refreshTokenRepository.deleteByUser(user);
    }

    private String hashToken(String token) {

        try {

            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));

            return HexFormat.of().formatHex(hash);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash refresh token", e);
        }
    }

}
