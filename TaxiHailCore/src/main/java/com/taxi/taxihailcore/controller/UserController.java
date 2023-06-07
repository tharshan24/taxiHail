package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.dto.PasswordResetDTO;
import com.taxi.taxihailcore.dto.UserDTO;
import com.taxi.taxihailcore.exceptions.UserRegistrationException;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.repository.UserRepository;
import com.taxi.taxihailcore.response.AuthenticationResponse;
import com.taxi.taxihailcore.response.CommonResponse;
import com.taxi.taxihailcore.service.LogoutService;
import com.taxi.taxihailcore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LogoutService logoutService;
    private final UserRepository userRepository;

    public UserController(UserService userService, LogoutService logoutService, UserRepository userRepository) {
        this.userService = userService;
        this.logoutService = logoutService;
        this.userRepository = userRepository;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Test ok");
    }

    @GetMapping("/logout")
    public ResponseEntity<AuthenticationResponse> logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        return ResponseEntity.ok(logoutService.logout(request, response, authentication));
    }

    @GetMapping("/view_user")
    public ResponseEntity<UserDTO> viewUser(@NotNull HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(userService.viewUser(userId).getBody());
    }

    @PostMapping("/update_user")
    public ResponseEntity<ResponseEntity> updateUser(
            @RequestBody UserDTO request,
            @NotNull HttpServletRequest httpServletRequest
    ) {
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        Optional<User> user = userRepository.findByUserId(userId);
        if (user.isPresent()) {
            User user1 = user.get();
            if (userRepository.existsByEmail(request.getEmail()) && !request.getEmail().equals(user1.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.builder()
                        .status(0)
                        .message("Email Already Exists")
                        .build()));
            }

            if (userRepository.existsByMobile(request.getMobile()) && !request.getMobile().equals(user1.getMobile())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.builder()
                        .status(0)
                        .message("Mobile Already Exists")
                        .build()));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CommonResponse.builder()
                    .status(0)
                    .message("User not found")
                    .build()));
        }
        return ResponseEntity.ok(userService.updateUser(request, userId));
    }

    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(
            @RequestBody @NotNull PasswordResetDTO request,
            HttpServletRequest httpServletRequest) {

        if (!request.getNewPassword().equals(request.getNewPasswordConfirm())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password doesn't match");
        }
        UUID userId = UUID.fromString((String) httpServletRequest.getAttribute("userId"));
        return userService.resetPassword(request, userId);
    }

}
