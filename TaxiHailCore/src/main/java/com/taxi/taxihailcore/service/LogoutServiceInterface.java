package com.taxi.taxihailcore.service;

import com.taxi.taxihailcore.response.AuthenticationResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface LogoutServiceInterface {
    AuthenticationResponse logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    );
}
