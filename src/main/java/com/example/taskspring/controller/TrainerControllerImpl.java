package com.example.taskspring.controller;

import com.example.taskspring.dto.PostUserResponseDTO;
import com.example.taskspring.dto.traineeDTO.BasicTraineeDTO;
import com.example.taskspring.dto.traineeDTO.GetTraineeResponseDTO;
import com.example.taskspring.dto.traineeDTO.PostTraineeRequestDTO;
import com.example.taskspring.dto.trainerDTO.*;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
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
import java.util.HashSet;
import java.util.Set;

import static com.example.taskspring.dto.DtoConverters.traineeTogetGetTraineeResponseDTO;

@RestController
@RequestMapping("/trainers")
@Slf4j
@NoArgsConstructor
public class TrainerControllerImpl implements TrainerController{

    private TrainerService trainerService;

    private TraineeService traineeService;
    @Autowired
    public TrainerControllerImpl(TrainerService trainerService, TraineeService traineeService) {
        this.trainerService = trainerService;
        this.traineeService = traineeService;
    }

    @PostMapping
    public ResponseEntity<PostUserResponseDTO> create(@RequestBody PostTrainerRequestDTO postTrainerRequestDTO) {
        log.info("Received POST request to create a trainer. Request details: {}", postTrainerRequestDTO);
        Trainer createdTrainer = trainerService.createTrainer(postTrainerRequestDTO.getFirstName(),
                postTrainerRequestDTO.getLastName(), true, postTrainerRequestDTO.getSpecialization());
        PostUserResponseDTO responseDTO = new PostUserResponseDTO(createdTrainer.getUsername(), createdTrainer.getPassword());
        ResponseEntity<PostUserResponseDTO> responseEntity = new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
        log.info("Trainer created successfully. Response details: {}", responseEntity);
        return responseEntity;
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetTrainerResponseDTO> get(@PathVariable String username) throws AuthenticationException {
        log.info("Received GET request to retrieve a trainer. Request details: {}", username);
        try {
            Trainer trainer = trainerService.selectTrainer(username, "admin", "admin");
            Set<BasicTraineeDTO> basicTraineeDTOs = new HashSet<>();
            for (Trainee t : trainer.getTrainees()) {
                basicTraineeDTOs.add(new BasicTraineeDTO(t.getUsername(), t.getFirstName(), t.getLastName()));
            }
            GetTrainerResponseDTO dto = new GetTrainerResponseDTO(trainer.getFirstName(), trainer.getLastName(), trainer.isActive(),
                    trainer.getSpecialization(), basicTraineeDTOs);
            ResponseEntity<GetTrainerResponseDTO> responseEntity = new ResponseEntity<>(dto, HttpStatus.OK);
            log.info("Trainer retrieved successfully. Response details: {}", responseEntity);
            return responseEntity;
        } catch (EntityNotFoundException e) {
            log.error("there is no trainer " + username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
