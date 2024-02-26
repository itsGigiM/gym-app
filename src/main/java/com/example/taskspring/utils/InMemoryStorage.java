package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
@Getter
@Setter
@Component
@Data
public class InMemoryStorage {
    private Map<Long, Trainee> traineesData = new HashMap<>();
    private Map<Long, Trainer> trainersData = new HashMap<>();
    private Map<Long, Training> trainingsData = new HashMap<>();

}
