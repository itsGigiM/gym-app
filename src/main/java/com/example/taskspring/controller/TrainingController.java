package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;

public interface TrainingController {
    public ResponseEntity<HttpStatus> create(PostTrainingRequest request, String username,
                                             String password) throws AuthenticationException;
}
