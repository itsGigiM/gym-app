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

    ResponseEntity<GetTraineeResponseDTO> get(String username, String user, String password);

    ResponseEntity<PutTraineeResponseDTO> put(String username, PutTraineeRequestDTO putTraineeRequestDTO, String user, String password)
           ;

    ResponseEntity<HttpStatus> delete(String username, String user, String password);

    ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO,
                                                                          String username, String password);

    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTraineeTrainingListRequestDTO request, String username, String password);

    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request, String username, String password);

    public ResponseEntity<GetUnassignedTrainersDTO> getUnassignedTrainers(String username, String user, String password);

}
