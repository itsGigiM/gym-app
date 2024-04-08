package com.example.taskspring.controller;

import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.dto.trainerDTO.BasicTrainerDTO;
import com.example.taskspring.dto.trainerDTO.GetUnassignedTrainersDTO;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.service.AuthenticationService;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import com.example.taskspring.actuator.metric.TraineeMetrics;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.Set;

import static com.example.taskspring.controller.TrainerControllerImpl.trainingToTrainingDTO;

@RestController
@RequestMapping("/trainees")
@Slf4j
@NoArgsConstructor
public class TraineeControllerImpl implements TraineeController{

    private TraineeService traineeService;
    private TrainerService trainerService;
    private TraineeMetrics traineeMetrics;
    @Autowired
    public TraineeControllerImpl(TraineeService traineeService, TrainerService trainerService, TraineeMetrics traineeMetrics) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.traineeMetrics = traineeMetrics;
    }

    @PostMapping
    public ResponseEntity<AuthenticationDTO> create(@RequestBody PostTraineeRequestDTO postTraineeRequestDTO) {
            log.info("Received POST request to create a trainee. Request details: {}", postTraineeRequestDTO);

            Trainee createdTrainee = traineeService.createTrainee(postTraineeRequestDTO.getFirstName(),
                    postTraineeRequestDTO.getLastName(), true, postTraineeRequestDTO.getAddress(),
                    postTraineeRequestDTO.getDateOfBirth());

            AuthenticationDTO authenticationDTO = new AuthenticationDTO(createdTrainee.getUsername(), createdTrainee.getPassword());
            ResponseEntity<AuthenticationDTO> responseEntity = new ResponseEntity<>(authenticationDTO, HttpStatus.CREATED);

            log.info("Trainee created successfully. Response details: {}", responseEntity);
            traineeMetrics.incrementCreateTrainee();
            return responseEntity;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetTraineeResponseDTO> get(@PathVariable String username) {
        log.info("Received GET request to retrieve a trainee. Request details: {}", username);
        Trainee trainee = traineeService.selectTrainee(username);

        Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
        for(Trainer t : trainee.getTrainers()) basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));
        GetTraineeResponseDTO dto = new GetTraineeResponseDTO(trainee.getFirstName(), trainee.getLastName(), trainee.isActive(),
                trainee.getAddress(), trainee.getDateOfBirth(), basicTrainerDTOs);
        ResponseEntity<GetTraineeResponseDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
        log.info("Trainee retrieved successfully. Response details: {}", responseEntity);
        traineeMetrics.incrementGetTrainee();
        return responseEntity;
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<PutTraineeResponseDTO> put(@PathVariable String username, @RequestBody PutTraineeRequestDTO putTraineeRequestDTO){

        log.info("Received PUT request to modify a trainee. Request details: {} {}", username, putTraineeRequestDTO);
        Trainee existingTrainee = traineeService.selectTrainee(username);

        existingTrainee.setFirstName(putTraineeRequestDTO.getFirstName());
        existingTrainee.setLastName(putTraineeRequestDTO.getLastName());
        existingTrainee.setDateOfBirth(putTraineeRequestDTO.getDateOfBirth());
        existingTrainee.setAddress(putTraineeRequestDTO.getAddress());
        existingTrainee.setActive(putTraineeRequestDTO.isActive());

        Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
        for(Trainer t : existingTrainee.getTrainers()) basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));

        Trainee updatedTrainee = traineeService.updateTrainee(existingTrainee.getUserId(), existingTrainee);
        PutTraineeResponseDTO responseDTO = new PutTraineeResponseDTO(updatedTrainee.getUsername(), updatedTrainee.getFirstName(),
                updatedTrainee.getLastName(), updatedTrainee.isActive(), updatedTrainee.getAddress(), updatedTrainee.getDateOfBirth(),
                basicTrainerDTOs);

        ResponseEntity<PutTraineeResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.OK);
        log.info("Trainee retrieved successfully. Response details: {}", responseEntity);
        traineeMetrics.incrementUpdateTrainee();
        return responseEntity;
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String username){

        log.info("Received DELETE request to remove a trainee. Request details: {}", username);
        traineeService.deleteTrainee(username);
        ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        log.info("Trainee removed successfully. Response details: {}", responseEntity);
        traineeMetrics.incrementDeleteTrainee();
        return responseEntity;
    }

    @PutMapping(value = "/trainer-list")
    public ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(@RequestBody UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO){

        log.info("Received PUT request to update trainer list of a trainee. Request details: {}", updateTraineeTrainingListRequestDTO);
        Trainee trainee = traineeService.selectTrainee(updateTraineeTrainingListRequestDTO.getUsername());
        Set<Trainer> trainerList = new HashSet<>();
        Set<TrainerListResponseTrainerDTO> trainerDTOList = new HashSet<>();
        for (String trainerUsername : updateTraineeTrainingListRequestDTO.getTrainersList()) {
            Trainer trainer = trainerService.selectTrainer(trainerUsername);
            trainerList.add(trainer);
            trainerDTOList.add(new TrainerListResponseTrainerDTO(trainer.getUsername(), trainer.getFirstName(), trainer.getLastName(),
                    trainer.getSpecialization()));
        }
        trainee.setTrainers(trainerList);

        traineeService.updateTrainee(trainee.getUserId(), trainee);
        ResponseEntity<UpdateTraineeTrainerListResponseDTO> responseEntity =
                new ResponseEntity<>(new UpdateTraineeTrainerListResponseDTO(trainerDTOList), HttpStatus.OK);

        log.info("Trainers list retrieved successfully. Response details: {}", responseEntity);
        traineeMetrics.incrementUpdateTrainee();
        return responseEntity;
    }

    @GetMapping(value = "/training-list")
    public ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(@ModelAttribute GetTraineeTrainingListRequestDTO request){

        log.info("Received GET request to retrieve training list of a trainee. Request details: {}", request);
        Set<Training> trainings = traineeService.getTraineeTrainingList(request.getUsername(), request.getFrom(), request.getTo(),
                request.getTrainerName(), request.getTrainingType());
        traineeMetrics.incrementGetTrainingList();
        return trainingToTrainingDTO(trainings, log);
    }

    @PatchMapping("/is-active")
    public ResponseEntity<HttpStatus> updateIsActive(@RequestBody PatchUserActiveStatusRequestDTO request){
        log.info("Received PATCH request to modify active status of a trainee. Request details: {}", request);
        Trainee t = traineeService.selectTrainee(request.getUsername());
        traineeService.activateDeactivateTrainee(t.getUserId(), request.isActive());
        ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        log.info("Trainee's activity status changed successfully. Response details: {}", response);
        traineeMetrics.incrementPatchTrainee();
        return response;
    }

    @GetMapping(value = "/unassigned-trainers/{username}")
    public ResponseEntity<GetUnassignedTrainersDTO> getUnassignedTrainers(@PathVariable String username){
        log.info("Received GET request to retrieve not assigned on trainee active trainers. Request details: {}", username);
            Set<Trainer> trainers = traineeService.getUnassignedTrainers(username);
            Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
            for (Trainer t : trainers) {
                basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));
            }
        GetUnassignedTrainersDTO dto = new GetUnassignedTrainersDTO(basicTrainerDTOs);
        ResponseEntity<GetUnassignedTrainersDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
        log.info("Trainers retrieved successfully. Response details: {}", responseEntity);
        traineeMetrics.incrementGetTrainee();
        return responseEntity;
    }

}
