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
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
//                                    FilterChain filterChain) throws ServletException, IOException {
//        if(!isCreateEndpoint(request)) {
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized access");
//                return;
//
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    private boolean isCreateEndpoint(HttpServletRequest request) {
//        return "POST".equals(request.getMethod()) &&
//                (request.getRequestURI().endsWith("/trainers") ||
//                        request.getRequestURI().endsWith("/trainees"));
//    }
//
//}
