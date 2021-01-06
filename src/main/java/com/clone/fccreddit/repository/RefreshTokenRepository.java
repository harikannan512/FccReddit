package com.clone.fccreddit.repository;

import com.clone.fccreddit.dto.RefreshTokenRequest;
import com.clone.fccreddit.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(RefreshTokenRequest token);
}
