package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface TrainingController {
    public ResponseEntity<HttpStatus> create(@RequestBody PostTrainingRequest request);
}
