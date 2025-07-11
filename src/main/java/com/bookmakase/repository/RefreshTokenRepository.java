package com.bookmakase.repository;

import com.bookmakase.domain.RefreshToken;
import com.bookmakase.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    void deleteByRefreshToken(String refreshToken);

    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken r WHERE r.user.userId = :userId")
    void deleteByUserId(Long userId);

    void deleteByUser(User user);
}
