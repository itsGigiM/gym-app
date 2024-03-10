package com.example.taskspring.controller;

import com.example.taskspring.dto.PostUserResponseDTO;
import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Training;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.naming.AuthenticationException;
import java.util.Set;

import static com.example.taskspring.dto.DtoConverters.*;

@RestController
@RequestMapping("/trainees")
@Slf4j
@NoArgsConstructor
public class TraineeControllerImpl implements TraineeController{

    private TraineeService traineeService;

    private TrainerService trainerService;
    @Autowired
    public TraineeControllerImpl(TraineeService traineeService, TrainerService trainerService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<PostUserResponseDTO> create(@RequestBody PostTraineeRequestDTO postTraineeRequestDTO) {
        log.info("Received POST request to create a trainee. Request details: {}", postTraineeRequestDTO);

        Trainee createdTrainee = traineeService.createTrainee(postTraineeRequestDTO.getFirstName(),
                postTraineeRequestDTO.getLastName(), true, postTraineeRequestDTO.getAddress(),
                postTraineeRequestDTO.getDateOfBirth());

        PostUserResponseDTO responseDTO = new PostUserResponseDTO(createdTrainee.getUsername(), createdTrainee.getPassword());
        ResponseEntity<PostUserResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);

        log.info("Trainee created successfully. Response details: {}", responseEntity);
        return responseEntity;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetTraineeResponseDTO> get(@PathVariable String username) throws AuthenticationException {
        log.info("Received GET request to retrieve a trainee. Request details: {}", username);
        try {
            Trainee trainee = traineeService.selectTrainee(username, "admin", "admin");

            GetTraineeResponseDTO dto = traineeTogetGetTraineeResponseDTO(trainee);

            ResponseEntity<GetTraineeResponseDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
            log.info("Trainee retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainee {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<PutTraineeResponseDTO> put(@PathVariable String username, @RequestBody PutTraineeRequestDTO putTraineeRequestDTO) throws AuthenticationException {
        log.info("Received PUT request to modify a trainee. Request details: {} {}", username, putTraineeRequestDTO);
        try {
            Trainee existingTrainee = traineeService.selectTrainee(username, "admin", "admin");

            putTraineeRequestDTOUpdates(existingTrainee, putTraineeRequestDTO);

            Trainee updatedTrainee = traineeService.updateTrainee(existingTrainee.getUserId(), existingTrainee, "admin", "admin");

            PutTraineeResponseDTO responseDTO = new PutTraineeResponseDTO(updatedTrainee.getUsername(), updatedTrainee.getFirstName(),
                    updatedTrainee.getLastName(), updatedTrainee.isActive(), updatedTrainee.getAddress(), updatedTrainee.getDateOfBirth(),
                    updatedTrainee.getTrainers());

            ResponseEntity<PutTraineeResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.OK);
            log.info("Trainee retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainee {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<HttpStatus> delete(@PathVariable String username) throws AuthenticationException {
        log.info("Received DELETE request to remove a trainee. Request details: {}", username);
        try {
            traineeService.deleteTrainee(username, "admin", "admin");
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
            log.info("Trainee removed successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainee {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/trainer-list")
    public ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(@RequestBody UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO) throws AuthenticationException {
        log.info("Received PUT request to update trainer list of a trainee. Request details: {}", updateTraineeTrainingListRequestDTO);
        try {
            Trainee trainee = traineeService.selectTrainee(updateTraineeTrainingListRequestDTO.getUsername(), "admin", "admin");
            Set<TrainerListResponseTrainerDTO> trainerDTOList = getTrainerListResponseTrainerDTOS(updateTraineeTrainingListRequestDTO, trainee, trainerService);
            traineeService.updateTrainee(trainee.getUserId(), trainee, "admin", "admin");
            ResponseEntity<UpdateTraineeTrainerListResponseDTO> responseEntity =
                    new ResponseEntity<>(new UpdateTraineeTrainerListResponseDTO(trainerDTOList), HttpStatus.OK);

            log.info("Trainers list retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("there is no user {}", updateTraineeTrainingListRequestDTO.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/training-list")
    public ResponseEntity<GetTraineeTrainingListResponseDTO> getTrainingList(@RequestBody GetTraineeTrainingListRequestDTO request) throws AuthenticationException {
        log.info("Received GET request to retrieve training list of a trainee. Request details: {}", request);
        try {
            Set<Training> trainings = traineeService.getTraineeTrainingList(request.getUsername(), request.getFrom(), request.getTo(),
                    request.getTrainerName(), request.getTrainingType());
            Set<TrainingListResponseTrainingDTO> trainingDTOList = getTrainingListResponseTrainingDTOS(trainings);
            ResponseEntity<GetTraineeTrainingListResponseDTO> response = new ResponseEntity<>(new GetTraineeTrainingListResponseDTO(trainingDTOList), HttpStatus.OK);
            log.info("Trainers list retrieved successfully. Response details: {}", response);
            return response;
        }catch (EntityNotFoundException e){
            log.error("there is no trainee {}", request.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/is-active")
    public ResponseEntity<HttpStatus> updateIsActive(@RequestBody PatchTraineeActiveStatusRequestDTO request) {
        log.info("Received PATCH request to modify active status of a trainee. Request details: {}", request);
        try {
            Trainee t = traineeService.selectTrainee(request.getUsername(), "admin", "admin");
            traineeService.activateDeactivateTrainee(t.getUserId(), request.isActive(), "admin", "admin");
            ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.OK);
            log.info("Trainers list retrieved successfully. Response details: {}", response);
            return response;
        } catch (Exception e) {
            log.error("there is no trainee {}", request.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
