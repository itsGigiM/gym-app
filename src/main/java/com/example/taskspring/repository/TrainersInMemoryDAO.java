package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TrainersInMemoryDAO implements TrainersDAO{
    private InMemoryStorage storage;
    public Trainer add(Trainer trainer) {
        storage.getTrainersData().put(trainer.getTrainerId(), trainer);
        return trainer;
    }

    public boolean exists(Long userId) {
        return storage.getTrainersData().containsKey(userId);
    }

    public Trainer get(Long userId) {
        return storage.getTrainersData().get(userId);
    }

    public List<Trainer> getAll() {
        return new ArrayList<>(storage.getTrainersData().values());
    }

    public Trainer set(Trainer trainer) {
        return add(trainer);
    }

    public String toString(){
        return storage.getTrainersData().toString();
    }
}
