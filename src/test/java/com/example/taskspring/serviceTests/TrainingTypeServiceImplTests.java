package com.example.taskspring.serviceTests;

import com.example.taskspring.model.TrainingType;
import com.example.taskspring.model.TrainingTypeEnum;
import com.example.taskspring.repository.repositories.TrainingTypeRepository;
import com.example.taskspring.service.TrainingTypeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TrainingTypeServiceImplTests {

    @Mock
    private TrainingTypeRepository repository;

    @InjectMocks
    private TrainingTypeServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new TrainingTypeServiceImpl(repository);
    }

    @Test
    public void testGetAll() {
        Set<TrainingType> mockTrainingTypes = new HashSet<>();
        when(repository.findAll()).thenReturn(mockTrainingTypes);

        Set<TrainingType> result = service.getAll();

        assertEquals(mockTrainingTypes, result);
    }

    @Test
    public void testGetById() {
        TrainingType mockTrainingType = new TrainingType(1L, TrainingTypeEnum.BOXING);
        when(repository.findTrainingTypeById(1L)).thenReturn(mockTrainingType);

        TrainingType result = service.getById(1L);

        assertEquals(mockTrainingType, result);
    }
}
