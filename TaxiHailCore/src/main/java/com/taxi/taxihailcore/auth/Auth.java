package com.taxi.taxihailcore.auth;

import com.taxi.taxihailcore.dto.RegisterRequestDTO;
import com.taxi.taxihailcore.exceptions.UserRegistrationException;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.response.AuthenticationResponse;
import com.taxi.taxihailcore.service.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Auth {
    private final AuthenticationService authenticationService;
    private final UserRepository userRepository;
    private final LogoutService logoutService;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequestDTO request) {
        // Check if the username or email already exists in the database
        if (userRepository.existsByUserName(request.getUsername())) {
            throw new UserRegistrationException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserRegistrationException("Email already exists");
        }

        if (userRepository.existsByMobile(request.getMobile())) {
            throw new UserRegistrationException("Mobile already exists");
        }

        if (!request.getPassword().equals(request.getPasswordConfirm())) {
            return ResponseEntity.ok(
                    AuthenticationResponse.builder()
                            .status(0)
                            .message("Password doesn't match")
                            .build()
            );
        }
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody RegisterRequestDTO request
    ) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }

    @GetMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {
        return ResponseEntity.ok(logoutService.logout(request, response, authentication));
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test no auth ok");
    }

}
