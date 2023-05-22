package com.taxi.taxihailcore.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;

public interface LogoutServiceInterface {
    void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    );
}
