package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.dto.trainerDTO.*;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.service.AuthenticationService;
import com.example.taskspring.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/trainers")
@Slf4j
@NoArgsConstructor
public class TrainerControllerImpl implements TrainerController{

    private TrainerService trainerService;

    private AuthenticationService authenticationService;


    @Autowired
    public TrainerControllerImpl(TrainerService trainerService, AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<AuthenticationDTO> create(@RequestBody PostTrainerRequestDTO postTrainerRequestDTO) {
        try {
            log.info("Received POST request to create a trainer. Request details: {}", postTrainerRequestDTO);
            Trainer createdTrainer = trainerService.createTrainer(postTrainerRequestDTO.getFirstName(),
                    postTrainerRequestDTO.getLastName(), true, postTrainerRequestDTO.getSpecialization());
            AuthenticationDTO responseDTO = new AuthenticationDTO(createdTrainer.getUsername(), createdTrainer.getPassword());
            ResponseEntity<AuthenticationDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
            log.info("Trainer created successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetTrainerResponseDTO> get(@PathVariable String username,  @RequestBody AuthenticationDTO authenticationDTO){
        if (isNotAuthorized(authenticationDTO)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received GET request to retrieve a trainer. Request details: {}", username);
        try {
            Trainer trainer = trainerService.selectTrainer(username);
            Set<BasicTraineeDTO> basicTraineeDTOs = new HashSet<>();
            for (Trainee t : trainer.getTrainees()) {
                basicTraineeDTOs.add(new BasicTraineeDTO(t.getUsername(), t.getFirstName(), t.getLastName()));
            }
            GetTrainerResponseDTO dto = new GetTrainerResponseDTO(trainer.getUsername(), trainer.getFirstName(),
                    trainer.getLastName(), trainer.isActive(), trainer.getSpecialization(), basicTraineeDTOs);
            ResponseEntity<GetTrainerResponseDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
            log.info("Trainer retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        } catch (EntityNotFoundException e) {
            log.error("there is no trainer " + username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<PutTrainerResponseDTO> put(@PathVariable String username, @RequestBody PutTrainerRequestDTO putTrainerRequestDTO,
                                                     @RequestBody AuthenticationDTO authenticationDTO){
        if (isNotAuthorized(authenticationDTO)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received PUT request to modify a trainer. Request details: {} {}", username, putTrainerRequestDTO);
        try {
            Trainer existingTrainer = trainerService.selectTrainer(username);

            existingTrainer.setUsername(putTrainerRequestDTO.getUsername());
            existingTrainer.setFirstName(putTrainerRequestDTO.getFirstName());
            existingTrainer.setLastName(putTrainerRequestDTO.getLastName());
            existingTrainer.setActive(putTrainerRequestDTO.isActive());

            Trainer updatedTrainer = trainerService.updateTrainer(existingTrainer.getUserId(), existingTrainer);

            Set<BasicTraineeDTO> basicTraineeDTOs = new HashSet<>();
            for (Trainee t : updatedTrainer.getTrainees()) {
                basicTraineeDTOs.add(new BasicTraineeDTO(t.getUsername(), t.getFirstName(), t.getLastName()));
            }

            PutTrainerResponseDTO responseDTO = new PutTrainerResponseDTO(updatedTrainer.getUsername(), updatedTrainer.getFirstName(),
                    updatedTrainer.getLastName(), updatedTrainer.isActive(), updatedTrainer.getSpecialization(), basicTraineeDTOs);

            ResponseEntity<PutTrainerResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.OK);
            log.info("Trainer retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainer {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/training-list")
    public ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(@RequestBody GetTrainerTrainingListRequestDTO request,
                                                                          @RequestBody AuthenticationDTO authenticationDTO){
        if (isNotAuthorized(authenticationDTO)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received GET request to retrieve training list of a trainer. Request details: {}", request);
        try {
            Set<Training> trainings = trainerService.getTrainerTrainingList(request.getUsername(), request.getFrom(), request.getTo(),
                    request.getTrainerName());

            return trainingToTrainingDTO(trainings, log);
        }catch (EntityNotFoundException e){
            log.error("there is no trainer {}", request.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    static ResponseEntity<GetUserTrainingListResponseDTO> trainingToTrainingDTO(Set<Training> trainings, Logger log) {
        Set<TrainingListResponseTrainingDTO> trainingDTOList = new HashSet<>();
        for(Training training : trainings){
            trainingDTOList.add(new TrainingListResponseTrainingDTO(training.getTrainingName(), training.getTrainingDate(),
                    training.getTrainingType(), training.getDuration(), training.getTrainingName()));
        }
        ResponseEntity<GetUserTrainingListResponseDTO> response = new ResponseEntity<>(new GetUserTrainingListResponseDTO(trainingDTOList), HttpStatus.OK);
        log.info("Trainers list retrieved successfully. Response details: {}", response);
        return response;
    }

    @PatchMapping("/is-active")
    public ResponseEntity<HttpStatus> updateIsActive(@RequestBody PatchUserActiveStatusRequestDTO request,  @RequestBody AuthenticationDTO authenticationDTO) {
        if (isNotAuthorized(authenticationDTO)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received PATCH request to modify active status of a trainer. Request details: {}", request);
        try {
            Trainer t = trainerService.selectTrainer(request.getUsername());
            trainerService.activateDeactivateTrainer(t.getUserId(), request.isActive());
            ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.OK);
            log.info("Trainer's activity status changed successfully. Response details: {}", response);
            return response;
        } catch (EntityNotFoundException e) {
            log.error("there is no trainer {}", request.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isNotAuthorized(AuthenticationDTO authenticationDTO) {
        try {
            authenticationService.authenticate(authenticationDTO.getUsername(), authenticationDTO.getPassword());
        } catch (AuthenticationException e) {
            return true;
        }
        return false;
    }
}
