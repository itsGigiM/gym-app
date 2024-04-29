package com.example.taskspring.handlers;

import com.example.taskspring.service.JwtService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class LogoutHandlerImpl implements LogoutHandler {

    private final JwtService jwtService;

    @Autowired
    public LogoutHandlerImpl(JwtService jwtService){
        this.jwtService = jwtService;
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String authHeader = request.getHeader("Authorization");
        String jwtToken = authHeader.substring(7);
        jwtService.removeToken(jwtToken);
        log.info("Token removed");
    }
}
