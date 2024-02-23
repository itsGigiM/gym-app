package com.example.taskspring.repository;

import com.example.taskspring.model.Training;
import com.example.taskspring.utils.InMemoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
@Repository
@AllArgsConstructor
public class TrainingsInMemoryDAO implements TrainingsDAO{
    private InMemoryStorage storage;
    public void add(Training training) {
        storage.getTrainingsData().put(training.getTrainingId(), training);
    }

    public boolean exists(String trainingId) { return storage.getTrainingsData().containsKey(trainingId); }

    public Training get(String userId) {
        return storage.getTrainingsData().get(userId);
    }

    public ArrayList<Training> getAll() {
        return new ArrayList<>(storage.getTrainingsData().values());
    }

    public String toString(){
        return storage.getTrainingsData().toString();
    }
}
