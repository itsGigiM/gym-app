//package com.example.taskspring.serviceTests;
//
//import com.example.taskspring.model.Trainee;
//import com.example.taskspring.repository.TraineesRepository;
//import com.example.taskspring.repository.TrainersRepository;
//import com.example.taskspring.utils.InMemoryStorage;
//import com.example.taskspring.utils.SQLAuthenticator;
//import com.example.taskspring.utils.SQLUsernameGenerator;
//import lombok.AllArgsConstructor;
//import org.junit.jupiter.api.*;
//import com.example.taskspring.service.TraineeService;
//
//
//import javax.naming.AuthenticationException;
//import java.time.LocalDate;
//import java.util.NoSuchElementException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@AllArgsConstructor
//public class TraineeServiceTests {
//    private TraineeService service;
//    private TraineesRepository traineesRepository;
//    private TrainersRepository trainersRepository;
//
//    private SQLAuthenticator authenticator;
//
//    @BeforeEach
//    public void setUpService() {
//        InMemoryStorage memo = new InMemoryStorage();
//        service = new TraineeService(traineesRepository,
//                new SQLUsernameGenerator(traineesRepository, trainersRepository), authenticator);
//    }
//    @Test
//    public void testCreateAndSelect() throws AuthenticationException {
//        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
//                "Tbilisi", LocalDate.of(2022, 2, 2));
//        assertEquals(service.selectTrainee(1033L, "Gigi.Epam", "password").getUsername(), "Gigi.Mirziashvili");
//    }
//
//    @Test
//    public void testDelete() {
//        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
//                "Tbilisi", LocalDate.of(2022, 2, 2));
//        service.deleteTrainee(1033L);
//        assertThrows(NoSuchElementException.class, () -> {
//            service.selectTrainee(1033L);
//        });
//    }
//
//    @Test
//    public void testUpdate() {
//        service.createTrainee("Gigi", "Mirziashvili", true, 1033L,
//                "Tbilisi", LocalDate.of(2022, 2, 2));
//        Trainee trainee = service.selectTrainee(1033L);
//        trainee.setFirstName("Epam");
//        service.updateTrainee(1033L, trainee);
//        assertEquals(service.selectTrainee(1033L).getFirstName(), "Epam");
//    }
//}
