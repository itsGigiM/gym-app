package com.example.taskspring.controller;

import com.example.taskspring.dto.PostTraineeRequestDTO;
import com.example.taskspring.dto.PostTraineeResponseDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.dto.GetTraineeResponseDTO;
import com.example.taskspring.service.TraineeService;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/trainees")
@AllArgsConstructor
@Slf4j
public class TraineeControllerImpl implements TraineeController{

    private final TraineeService traineeService;

    @PostMapping
    public ResponseEntity<PostTraineeResponseDTO> create(@RequestBody PostTraineeRequestDTO postTraineeRequestDTO) {
        Trainee createdTrainee = traineeService.createTrainee(postTraineeRequestDTO.getFirstName(),
                postTraineeRequestDTO.getLastName(), true, postTraineeRequestDTO.getAddress(),
                postTraineeRequestDTO.getDateOfBirth());
        PostTraineeResponseDTO responseDTO = new PostTraineeResponseDTO(createdTrainee.getUsername(), createdTrainee.getPassword());
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{username}")
    public ResponseEntity<GetTraineeResponseDTO> get(@PathVariable String username) throws AuthenticationException {
        try {
            Trainee trainee = traineeService.selectTrainee(username, "admin", "admin");
            GetTraineeResponseDTO dto = new GetTraineeResponseDTO(trainee.getFirstName(), trainee.getLastName(), trainee.isActive(),
                    trainee.getAddress(), trainee.getDateOfBirth(), trainee.getTrainers());
            log.info("get " + username + " returned: " + dto);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        }catch (EntityNotFoundException e){
            log.info("there is no trainee " + username);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
