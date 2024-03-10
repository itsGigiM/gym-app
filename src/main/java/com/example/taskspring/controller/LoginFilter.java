//package com.example.taskspring.controller;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@WebFilter("/")
//public class LoginFilter extends OncePerRequestFilter {
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//            FilterChain filterChain) throws ServletException, IOException {
//
//        String username = request.getHeader("username");
//        String password = request.getHeader("password");
//
//        User user = authService.authenticate(username, password);
//
//    }
//    private boolean isCreateEndpoint(HttpServletRequest request) {
//        return "POST".equals(request.getMethod()) &&
//                (request.getRequestURI().endsWith("/trainers") ||
//                        request.getRequestURI().endsWith("/trainees"));
//    }
//
//}
