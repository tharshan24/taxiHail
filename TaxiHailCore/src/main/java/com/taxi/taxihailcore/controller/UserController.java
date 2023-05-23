package com.taxi.taxihailcore.controller;

import com.taxi.taxihailcore.auth.AuthenticationResponse;
import com.taxi.taxihailcore.dto.UserDTO;
import com.taxi.taxihailcore.model.User;
import com.taxi.taxihailcore.service.LogoutService;
import com.taxi.taxihailcore.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final LogoutService logoutService;

    public UserController(UserService userService, LogoutService logoutService){
        this.userService = userService;
        this.logoutService = logoutService;
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
    public ResponseEntity<UserDTO> viewUser(HttpServletRequest request) {
        UUID userId = UUID.fromString((String) request.getAttribute("userId"));
        return ResponseEntity.ok(userService.viewUser(userId).getBody());
    }

    @PostMapping("/update_user")
    public ResponseEntity<ResponseEntity> updateUser(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(userService.updateUser(request));
    }

}
