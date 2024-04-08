package com.example.taskspring.service;

import com.example.taskspring.model.User;
import com.example.taskspring.model.UserDetailsModel;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String generateToken(User user);

    Claims extractClaims(String token);

    boolean isValid(String token, UserDetailsModel user);

    public void removeToken(String token);
}
