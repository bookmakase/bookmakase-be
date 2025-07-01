package com.bookmakase.service;


import com.bookmakase.domain.RefreshToken;
import com.bookmakase.domain.User;
import com.bookmakase.repository.RefreshTokenRepository;
import com.bookmakase.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RefreshTokenService {

    @Value("${jwt.refresh-expiration}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public RefreshToken createRefreshToken(String email) {

        log.info("Creating new refresh token for user {}", email);

        // TODO: 사용자 이름(username)을 기반으로 새로운 Refresh Token을 생성하고 저장하는 로직을 구현합니다.
        // 1. UserRepository를 사용하여 사용자 정보를 조회합니다. 사용자가 없으면 예외를 발생시킵니다.
        User user = userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(email));

        log.info("Creating new refresh token for user {}", user);

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(user);
        log.info("Creating new refresh token for user1 {}", user.getEmail());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs * 1000));
        log.info("Creating new refresh token for user2 {}", user.getEmail());
        refreshToken.setToken(UUID.randomUUID().toString());
        log.info("Creating new refresh token for user3 {}", user.getEmail());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        // (1) 토큰의 만료 시간(`getExpiryDate()`)이 현재 시간 (`Instant.now()`)보다 이전인지 확인
        // (2) 만료되었다면, 데이터베이스에서 해당 토큰을 삭제하고 예외를 발생시킵니다.
        // (3) 만료되지 않았다면, 원래의 토큰을 그대로 반환합니다.
        if(refreshToken.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(refreshToken);

            throw new RuntimeException("Expired refresh token");
        }
        return refreshToken;
    }

    @Transactional
    public int deleteByUsername(String username) {
        return userRepository.findByUsername(username).map(user -> {
            refreshTokenRepository.deleteByUserId(user.getUserId());
            return 1;
        }).orElse(0);

    }
}
