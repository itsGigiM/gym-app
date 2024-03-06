package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Getter
@Component
public class InMemoryStorage {
    private final Map<Long, Trainee> traineesData = new HashMap<>();
    private final Map<Long, Trainer> trainersData = new HashMap<>();
    private final Map<Long, Training> trainingsData = new HashMap<>();

}