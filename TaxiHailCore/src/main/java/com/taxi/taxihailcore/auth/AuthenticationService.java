package com.taxi.taxihailcore.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.taxi.taxihailcore.dto.RegisterRequestDTO;
import com.taxi.taxihailcore.model.Role;
import com.taxi.taxihailcore.model.Token;
import com.taxi.taxihailcore.model.TokenType;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.TokenRepository;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequestDTO request) {


        try{
            var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .userName(request.getUsername())
                .mobile(request.getMobile())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.valueOf(request.getRole()))
                .status(1)
                .build();

            var savedUser = userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);

            saveUserToken(savedUser, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .status(200)
                    .message("User registration successful")
                    .userId(String.valueOf(savedUser.getUserId()))
                    .username(savedUser.getUsername())
                    .role(String.valueOf(savedUser.getRole()))
                    .build();

        } catch (Exception e) {
            return AuthenticationResponse.builder()
                    .status(0)
                    .message("User Regisstration Failed: " + e.getMessage())
                    .build();
        }

    }

    public AuthenticationResponse authenticate(@NotNull RegisterRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            var user = userRepository.findByUserNameAndStatus(request.getUsername(), 1).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = jwtService.generateRefreshToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            user.setLastLogin(Timestamp.from(Instant.now()));
            userRepository.save(user);

            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .status(200)
                    .message("User authentication successful")
                    .userId(String.valueOf(user.getUserId()))
                    .username(user.getUsername())
                    .role(String.valueOf(user.getRole()))
                    .build();

        } catch (AuthenticationException ex) {
            return AuthenticationResponse.builder()
                    .status(0)
                    .message("User authentication Failed: " + ex.getMessage())
                    .build();
        }
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .authUser(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(@NotNull User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getUserId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public void refreshToken(
            @NotNull HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userName;
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userName = jwtService.extractUserName(refreshToken);
        if (userName != null) {
            var user = this.userRepository.findByUserNameAndStatus(userName, 1)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
