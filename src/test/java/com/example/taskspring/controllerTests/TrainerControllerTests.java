package com.example.taskspring.controllerTests;

import com.example.taskspring.controller.TrainerControllerImpl;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.trainerDTO.*;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.model.TrainingType;
import com.example.taskspring.service.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TrainerControllerTests {
    @Mock
    private TrainingTypeService trainingTypeService;

    @Mock
    private TrainerService trainerService;

    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private TrainerControllerImpl trainerController;

    @BeforeEach
    public void setUp() {
        trainerController = new TrainerControllerImpl(trainerService, authenticationService, trainingTypeService);
    }

    @Test
    public void createTrainer() {
        Trainer t = new Trainer("g", "m", "g.m", "pass",
                true, new TrainingType());

        PostTrainerRequestDTO dto = new PostTrainerRequestDTO("g", "m",
                1L);

        when(trainerService.createTrainer("g", "m", true, null)).thenReturn(t);

        AuthenticationDTO authenticationDTO = new AuthenticationDTO(t.getUsername(), t.getPassword());
        assertEquals(trainerController.create(dto), new ResponseEntity<>(authenticationDTO, HttpStatus.CREATED));
    }

    @Test
    public void retrieveTrainee() throws AuthenticationException {

        Trainer t = new Trainer("g", "m", "g.m", "pass",
                true, new TrainingType());

        when(trainerService.selectTrainer(anyString())).thenReturn(t);

        doNothing().when(authenticationService).authenticate(anyString(), anyString());

        GetTrainerResponseDTO dto1 = new GetTrainerResponseDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.isActive(),
                new TrainingType(), new HashSet<>());

        assertEquals(trainerController.get("g.m", "admin", "admin"),
                new ResponseEntity<>(dto1, HttpStatus.OK));
    }

    @Test
    public void modifyTrainee() throws AuthenticationException {

        Trainer t = new Trainer("g", "m", "g.m", "pass",
                true, new TrainingType());

        when(trainerService.selectTrainer(anyString())).thenReturn(t);
        doNothing().when(authenticationService).authenticate(anyString(), anyString());

        PutTrainerRequestDTO dto1 = new PutTrainerRequestDTO(t.getFirstName(), "epam", t.isActive(),
                2L);

        PutTrainerResponseDTO dto2 = new PutTrainerResponseDTO(t.getUsername(), t.getFirstName(),
                "epam", t.isActive(), null, new HashSet<>());

        t.setLastName("epam");
        when(trainerService.updateTrainer(any(), any())).thenReturn(t);

        assertEquals(trainerController.put("g.m", dto1, "admin", "admin"),
                new ResponseEntity<>(dto2, HttpStatus.OK));
    }

    @Test
    public void updateIsActive() throws AuthenticationException {
        PatchUserActiveStatusRequestDTO requestDTO = new PatchUserActiveStatusRequestDTO("traineeUsername", true);
        Trainer trainer = new Trainer();
        when(trainerService.selectTrainer(requestDTO.getUsername())).thenReturn(trainer);

        ResponseEntity<HttpStatus> response = trainerController.updateIsActive(requestDTO, "username", "password");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getTrainingList() throws AuthenticationException {
        GetTrainerTrainingListRequestDTO requestDTO = new GetTrainerTrainingListRequestDTO("trainerUsername", LocalDate.now(), LocalDate.now(), "traineeName");
        Set<Training> trainings = new HashSet<>();
        doNothing().when(authenticationService).authenticate(anyString(), anyString());
        when(trainerService.getTrainerTrainingList(anyString(), any(), any(), anyString()))
                .thenReturn(trainings);

        ResponseEntity<GetUserTrainingListResponseDTO> response = trainerController.getTrainingList(requestDTO, "username", "password");

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
