package com.example.taskspring.service;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@NoArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
    private final Map<String, String> users = new HashMap<>();

}
