package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.trainerDTO.*;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

public interface TrainerController {

    ResponseEntity<AuthenticationDTO> create(PostTrainerRequestDTO postTrainerRequestDTO);

    ResponseEntity<GetTrainerResponseDTO> get(String username, String user, String password) throws AuthenticationException;

    ResponseEntity<PutTrainerResponseDTO> put(String username, PutTrainerRequestDTO putTrainerRequestDTO,
                                              String user, String password) throws AuthenticationException;

    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTrainerTrainingListRequestDTO request,
                                                                   String username, String password) throws AuthenticationException;

    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request, String username, String password) throws AuthenticationException;
}
