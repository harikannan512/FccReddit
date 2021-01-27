package com.clone.fccreddit.service;

import com.clone.fccreddit.dto.RefreshTokenRequest;
import com.clone.fccreddit.exceptions.SpringRedditException;
import com.clone.fccreddit.model.RefreshToken;
import com.clone.fccreddit.repository.RefreshTokenRepository;
import com.clone.fccreddit.security.JwtProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;


    public RefreshToken generateRefreshToken(){
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validRefreshToken(RefreshTokenRequest token, String username) throws Exception{
        String requestUsername = token.getUsername();
        if(requestUsername.equals(username)) {
            refreshTokenRepository.findByToken(token.getRefreshToken())
                    .orElseThrow(() -> new SpringRedditException("Invalid refresh token"));
        } else {
            throw new SpringRedditException("Sneaky peaky eh? You are not " + username);
        }
    }

    public void deleteRefreshToken(RefreshTokenRequest token){
        refreshTokenRepository.deleteByToken(token);
    }
}
