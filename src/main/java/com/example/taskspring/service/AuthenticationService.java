package com.example.taskspring.service;

import com.example.taskspring.dto.loginDTO.TokenDTO;
import com.example.taskspring.repository.repositories.AuthenticationRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

public interface AuthenticationService {
    public TokenDTO authenticate(String username, String password) throws AuthenticationException;
}
