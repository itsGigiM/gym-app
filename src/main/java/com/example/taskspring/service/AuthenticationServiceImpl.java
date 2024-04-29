package com.example.taskspring.service;

import com.example.taskspring.dto.loginDTO.TokenDTO;
import com.example.taskspring.model.Token;
import com.example.taskspring.model.User;
import com.example.taskspring.repository.repositories.TokenRepository;
import com.example.taskspring.repository.repositories.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.AuthenticationException;

@Service
@Transactional
@Slf4j
@NoArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;
    private TokenRepository tokenRepository;
    private UserRepository userRepository;
    private LoginAttemptService loginAttemptService;
    @Autowired
    public AuthenticationServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager,
                                     TokenRepository tokenRepository, UserRepository userRepository,
                                     LoginAttemptService loginAttemptService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.tokenRepository = tokenRepository;
        this.userRepository = userRepository;
        this.loginAttemptService = loginAttemptService;
    }

    public TokenDTO authenticate(String username, String password) throws AuthenticationException {
        if(loginAttemptService.isBlocked()) throw new SecurityException();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
        } catch (org.springframework.security.core.AuthenticationException e) {
            throw new AuthenticationException();
        }
        String errorMessage = "User not found with username: " + username;
        User user = userRepository.findByUsername(username).orElseThrow(()
                -> new UsernameNotFoundException(errorMessage));
        log.info("Found user {}", user);
        String token = jwtService.generateToken(user);
        tokenRepository.save(new Token(token, user));
        return new TokenDTO(token);
    }

}
