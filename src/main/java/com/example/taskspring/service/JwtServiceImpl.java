package com.example.taskspring.service;

import com.example.taskspring.model.User;
import com.example.taskspring.model.UserDetailsModel;
import com.example.taskspring.repository.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.NoSuchElementException;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService{
    private final String SECRET_KEY;
    private final TokenRepository tokenRepository;

    @Autowired
    public JwtServiceImpl(@Value("${secret.key}") String SECRET_KEY, TokenRepository tokenRepository) {
        this.SECRET_KEY = SECRET_KEY;
        this.tokenRepository = tokenRepository;
    }

    public String generateToken(User user) {
        return Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000 ))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims extractClaims(String token) {
        return Jwts
                .parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean isValid(String token, UserDetailsModel user) {
        String username = extractClaims(token).getSubject();

        boolean validToken = tokenRepository
                .findByToken(token).isPresent()
                && extractClaims(token).getExpiration().after(new Date());

        return (username.equals(user.getUsername())) && validToken;
    }

    public void removeToken(String token){
        tokenRepository.delete(tokenRepository.findByToken(token).orElseThrow(
                () -> new IllegalStateException("Token not found")
        ));
    }
}

