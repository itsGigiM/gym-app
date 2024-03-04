package com.example.taskspring.serviceTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import com.example.taskspring.service.TraineeService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.naming.AuthenticationException;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TraineeServiceTests {

    @Mock
    private TraineesRepository traineesRepository;

    @InjectMocks
    private TraineeService service;

    String password;

    @BeforeEach
    public void setUpFirstTrainee() {
        password = service.createTrainee("admin", "admin", true,
                "address", LocalDate.of(2000, 1, 1)).getUser().getPassword();
    }
    @Test
    public void CreateTraineeAndSelectItsFirstName() throws AuthenticationException {
        Trainee mockedTrainee = new Trainee("firstname", "lastname", "username", "password",
                true, "address", LocalDate.of(2000, 1, 1));
        when(traineesRepository.save(Mockito.any(Trainee.class))).thenReturn(mockedTrainee);
        when(traineesRepository.findById(anyLong())).thenReturn(Optional.of(mockedTrainee));

        Trainee savedTrainee = service.createTrainee("firstname", "lastname", true,
                "address", LocalDate.of(2000, 1, 1));
        Trainee selectedTrainee = service.selectTrainee(savedTrainee.getTraineeId(), "admin.admin", password);

        assertEquals("firstName", savedTrainee.getUser().getFirstName());
        assertEquals("firstName", selectedTrainee.getUser().getFirstName());
    }

    @Test
    public void DeleteUserAndRetrieve_ThrowsException() throws AuthenticationException {
        Trainee id = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        service.deleteTrainee(id.getTraineeId(), "admin.admin", password);
        assertThrows(EntityNotFoundException.class, () -> {
            service.selectTrainee(id.getTraineeId(), "admin.admin", password);
        });
    }

    @Test
    public void UpdateTraineesFirstName() throws AuthenticationException {
        Trainee id = service.createTrainee("Gigi", "Mirziashvili", true,
                "Tbilisi", LocalDate.of(2022, 2, 2));
        Trainee trainee = service.selectTrainee(id.getTraineeId(), "admin.admin", password);
        trainee.getUser().setFirstName("Epam");
        service.updateTrainee(id.getTraineeId(), trainee, "admin.admin", password);
        assertEquals("Epam", service.selectTrainee(id.getTraineeId(), "admin.admin", password).
                getUser().getFirstName());
    }
}
