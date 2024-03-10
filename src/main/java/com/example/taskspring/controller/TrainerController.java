package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.trainerDTO.*;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface TrainerController {

    ResponseEntity<AuthenticationDTO> create(PostTrainerRequestDTO postTrainerRequestDTO);

    ResponseEntity<GetTrainerResponseDTO> get(String username);

    ResponseEntity<PutTrainerResponseDTO> put(String username, PutTrainerRequestDTO putTrainerRequestDTO);

    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTrainerTrainingListRequestDTO request);

    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request);
}
