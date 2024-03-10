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

    ResponseEntity<GetTraineeResponseDTO> get(String username);

    ResponseEntity<PutTraineeResponseDTO> put(String username, PutTraineeRequestDTO putTraineeRequestDTO)
           ;

    ResponseEntity<HttpStatus> delete(String username);

    ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO)
           ;

    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTraineeTrainingListRequestDTO request);

    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request);

    public ResponseEntity<GetUnassignedTrainersDTO> getUnassignedTrainers(@PathVariable String username);

}
