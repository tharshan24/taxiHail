package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.auth.AuthenticationResponse;
import com.taxi.taxihailcore.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutServiceInterface {

    private final TokenRepository tokenRepository;

    @Override
    public AuthenticationResponse logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return AuthenticationResponse.builder()
                    .accessToken(null)
                    .refreshToken(null)
                    .status(0)
                    .message("Logout Unsuccessful")
                    .build();
        }
        jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if (storedToken != null) {
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
        }
        return AuthenticationResponse.builder()
                .accessToken(null)
                .refreshToken(null)
                .status(200)
                .message("Logout Successful")
                .build();
    }
}
