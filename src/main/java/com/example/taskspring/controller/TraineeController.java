package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.dto.trainerDTO.GetUnassignedTrainersDTO;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

public interface TraineeController {

    ResponseEntity<AuthenticationDTO> create(PostTraineeRequestDTO postTraineeRequestDTO);

    ResponseEntity<GetTraineeResponseDTO> get(String username, AuthenticationDTO authenticationDTO);

    ResponseEntity<PutTraineeResponseDTO> put(String username, PutTraineeRequestDTO putTraineeRequestDTO, AuthenticationDTO authenticationDTO)
           ;

    ResponseEntity<HttpStatus> delete(String username, AuthenticationDTO authenticationDTO);

    ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO,
                                                                          AuthenticationDTO authenticationDTO);

    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTraineeTrainingListRequestDTO request, AuthenticationDTO authenticationDTO);

    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request, AuthenticationDTO authenticationDTO);

    public ResponseEntity<GetUnassignedTrainersDTO> getUnassignedTrainers(String username, AuthenticationDTO authenticationDTO);

}
