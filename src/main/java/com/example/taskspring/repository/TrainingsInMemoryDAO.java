package com.example.taskspring.repository;

import com.example.taskspring.model.Training;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@AllArgsConstructor
public class TrainingsInMemoryDAO implements TrainingsDAO{
    private InMemoryStorage storage;
    public void add(Training training) {
        storage.getTrainingsData().put(training.getTrainingId(), training);
    }

    public boolean exists(Long trainingId) { return storage.getTrainingsData().containsKey(trainingId); }

    public Training get(Long trainingId) {
        return storage.getTrainingsData().get(trainingId);
    }

    public ArrayList<Training> getAll() {
        return new ArrayList<>(storage.getTrainingsData().values());
    }

    public String toString(){
        return storage.getTrainingsData().toString();
    }
}
