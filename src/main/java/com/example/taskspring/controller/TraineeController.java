package com.example.taskspring.controller;

import com.example.taskspring.dto.PostUserResponseDTO;
import com.example.taskspring.dto.traineeDTO.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

public interface TraineeController {

    ResponseEntity<PostUserResponseDTO> create(PostTraineeRequestDTO postTraineeRequestDTO);

    ResponseEntity<GetTraineeResponseDTO> get(String username) throws AuthenticationException;

    ResponseEntity<PutTraineeResponseDTO> put(String username, PutTraineeRequestDTO putTraineeRequestDTO)
            throws AuthenticationException;

    ResponseEntity<HttpStatus> delete(String username) throws AuthenticationException;

    ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO)
            throws AuthenticationException;

    ResponseEntity<GetTraineeTrainingListResponseDTO> getTrainingList(GetTraineeTrainingListRequestDTO request) throws AuthenticationException;

    ResponseEntity<HttpStatus> updateIsActive(PatchTraineeActiveStatusRequestDTO request);
}
