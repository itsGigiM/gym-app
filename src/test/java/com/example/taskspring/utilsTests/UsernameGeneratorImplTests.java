package com.example.taskspring.utilsTests;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import com.example.taskspring.utils.UsernameGeneratorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsernameGeneratorImplTests {

    @Mock
    private TraineesRepository traineesRepository;

    @Mock
    private TrainersRepository trainersRepository;

    @InjectMocks
    private UsernameGeneratorImpl usernameGenerator;

    @Test
    void generateUsername() {
        when(traineesRepository.findByUserUsername("G.M")).thenReturn(Optional.empty());
        when(trainersRepository.findByUserUsername("G.M")).thenReturn(Optional.empty());

        String generatedUsername = usernameGenerator.generateUsername("G", "M");

        assertEquals("G.M", generatedUsername);
    }

    @Test
    void generateUsernameWithDuplicate() {
        when(traineesRepository.findByUserUsername("G.M")).thenReturn(Optional.of(new Trainee()));

        String generatedUsername = usernameGenerator.generateUsername("G", "M");

        assertTrue(generatedUsername.startsWith("G.M1"));
    }
    
}
