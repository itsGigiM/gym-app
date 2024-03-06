package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class JsonDataContainer {
    @JsonProperty("Trainees")
    private final List<Trainee> trainees = new ArrayList<>();
    @JsonProperty("Trainers")
    private final List<Trainer> trainers = new ArrayList<>();
    @JsonProperty("Trainings")
    private final List<Training> trainings = new ArrayList<>();
}
