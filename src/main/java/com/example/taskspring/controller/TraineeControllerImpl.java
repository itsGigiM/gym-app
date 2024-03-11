package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
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
import com.example.taskspring.service.TrainingTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.persistence.EntityNotFoundException;
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

    private AuthenticationService authenticationService;

    @Autowired
    public TraineeControllerImpl(TraineeService traineeService, TrainerService trainerService,
                                 AuthenticationService authenticationService) {
        this.traineeService = traineeService;
        this.trainerService = trainerService;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    @Operation(summary = "Register trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee Registered")})
    public ResponseEntity<AuthenticationDTO> create(@RequestBody PostTraineeRequestDTO postTraineeRequestDTO) {
        try {
            log.info("Received POST request to create a trainee. Request details: {}", postTraineeRequestDTO);

            Trainee createdTrainee = traineeService.createTrainee(postTraineeRequestDTO.getFirstName(),
                    postTraineeRequestDTO.getLastName(), true, postTraineeRequestDTO.getAddress(),
                    postTraineeRequestDTO.getDateOfBirth());

            AuthenticationDTO authenticationDTO = new AuthenticationDTO(createdTrainee.getUsername(), createdTrainee.getPassword());
            ResponseEntity<AuthenticationDTO> responseEntity = new ResponseEntity<>(authenticationDTO, HttpStatus.CREATED);

            log.info("Trainee created successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/{username}")
    @Operation(summary = "Retrieve user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<GetTraineeResponseDTO> get(@PathVariable String username, @RequestParam String user, @RequestParam String password) {
        if (isNotAuthorized(user, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received GET request to retrieve a trainee. Request details: {}", username);
        try {
            Trainee trainee = traineeService.selectTrainee(username);

            Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
            for(Trainer t : trainee.getTrainers()){
                basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));
            }
            GetTraineeResponseDTO dto = new GetTraineeResponseDTO(trainee.getFirstName(), trainee.getLastName(), trainee.isActive(),
                    trainee.getAddress(), trainee.getDateOfBirth(), basicTrainerDTOs);
            ResponseEntity<GetTraineeResponseDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
            log.info("Trainee retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainee {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{username}")
    @Operation(summary = "Modify trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified trainee"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<PutTraineeResponseDTO> put(@PathVariable String username, @RequestBody PutTraineeRequestDTO putTraineeRequestDTO,
                                                     @RequestParam String user, @RequestParam String password) {
        if (isNotAuthorized(user, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received PUT request to modify a trainee. Request details: {} {}", username, putTraineeRequestDTO);
        try {
            Trainee existingTrainee = traineeService.selectTrainee(username);

            existingTrainee.setFirstName(putTraineeRequestDTO.getFirstName());
            existingTrainee.setLastName(putTraineeRequestDTO.getLastName());
            existingTrainee.setDateOfBirth(putTraineeRequestDTO.getDateOfBirth());
            existingTrainee.setAddress(putTraineeRequestDTO.getAddress());
            existingTrainee.setActive(putTraineeRequestDTO.isActive());

            Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
            for(Trainer t : existingTrainee.getTrainers()){
                basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));
            }

            Trainee updatedTrainee = traineeService.updateTrainee(existingTrainee.getUserId(), existingTrainee);
            PutTraineeResponseDTO responseDTO = new PutTraineeResponseDTO(updatedTrainee.getUsername(), updatedTrainee.getFirstName(),
                    updatedTrainee.getLastName(), updatedTrainee.isActive(), updatedTrainee.getAddress(), updatedTrainee.getDateOfBirth(),
                    basicTrainerDTOs);

            ResponseEntity<PutTraineeResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.OK);
            log.info("Trainee retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainee {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{username}")
    @Operation(summary = "Remove user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed trainer"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<HttpStatus> delete(@PathVariable String username, @RequestParam String user, @RequestParam String password){
        if (isNotAuthorized(user, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received DELETE request to remove a trainee. Request details: {}", username);
        try {
            traineeService.deleteTrainee(username);
            ResponseEntity<HttpStatus> responseEntity = new ResponseEntity<>(HttpStatus.OK);
            log.info("Trainee removed successfully. Response details: {}", responseEntity);
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("There is no trainee {}", username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping(value = "/trainer-list")
    @Operation(summary = "Update trainee's trainers list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the list"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(@RequestBody UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO,
            @RequestParam String username, @RequestParam String password) {
        if (isNotAuthorized(username, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received PUT request to update trainer list of a trainee. Request details: {}", updateTraineeTrainingListRequestDTO);
        try {
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
            return responseEntity;
        }catch (EntityNotFoundException e){
            log.error("there is no user {}", updateTraineeTrainingListRequestDTO.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/training-list")
    @Operation(summary = "Retrieve trainings list by trainee's username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(@ModelAttribute GetTraineeTrainingListRequestDTO request,
            @RequestParam String username, @RequestParam String password){
        if (isNotAuthorized(username, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received GET request to retrieve training list of a trainee. Request details: {}", request);
        try {
            Set<Training> trainings = traineeService.getTraineeTrainingList(request.getUsername(), request.getFrom(), request.getTo(),
                    request.getTrainerName(), request.getTrainingType());

            return trainingToTrainingDTO(trainings, log);
        }catch (EntityNotFoundException e){
            log.error("there is no trainee {}", request.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/is-active")
    @Operation(summary = "Modify active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified active status"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<HttpStatus> updateIsActive(@RequestBody PatchUserActiveStatusRequestDTO request, @RequestParam String username, @RequestParam String password) {
        if (isNotAuthorized(username, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received PATCH request to modify active status of a trainee. Request details: {}", request);
        try {
            Trainee t = traineeService.selectTrainee(request.getUsername());
            traineeService.activateDeactivateTrainee(t.getUserId(), request.isActive());
            ResponseEntity<HttpStatus> response = new ResponseEntity<>(HttpStatus.OK);
            log.info("Trainee's activity status changed successfully. Response details: {}", response);
            return response;
        } catch (EntityNotFoundException e) {
            log.error("there is no trainee {}", request.getUsername());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (NullPointerException e){
            log.error("One of the required parameter is null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/unassigned-trainers/{username}")
    @Operation(summary = "Retrieve Unassigned trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<GetUnassignedTrainersDTO> getUnassignedTrainers(@PathVariable String username, @RequestParam String user, @RequestParam String password){
        if (isNotAuthorized(user, password)){
            log.error("Incorrect authentication details");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        log.info("Received GET request to retrieve not assigned on trainee active trainers. Request details: {}", username);
        try {
            Set<Trainer> trainers = traineeService.getUnassignedTrainers(username);
            Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
            for (Trainer t : trainers) {
                basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));
            }
            GetUnassignedTrainersDTO dto = new GetUnassignedTrainersDTO(basicTrainerDTOs);
            ResponseEntity<GetUnassignedTrainersDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
            log.info("Trainers retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        } catch (EntityNotFoundException e) {
            log.error("there is no trainee " + username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private boolean isNotAuthorized(String username, String password) {
        try {
            authenticationService.authenticate(username, password);
        } catch (AuthenticationException e) {
            return true;
        }
        return false;
    }

}
