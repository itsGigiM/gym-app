package com.example.taskspring.controller;

import com.example.taskspring.dto.PostTraineeRequestDTO;
import com.example.taskspring.dto.PostTraineeResponseDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.dto.GetTraineeResponseDTO;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.time.LocalDate;

public interface TraineeController {

    ResponseEntity<PostTraineeResponseDTO> create(PostTraineeRequestDTO postTraineeRequestDTO);
    ResponseEntity<GetTraineeResponseDTO> get(String username) throws AuthenticationException;
}
