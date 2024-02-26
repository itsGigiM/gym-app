package com.example.taskspring.repository;

import com.example.taskspring.model.Trainer;
import com.example.taskspring.utils.InMemoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@AllArgsConstructor
public class TrainersInMemoryDAO implements TrainersDAO{
    private InMemoryStorage storage;
    public void add(Trainer trainer) {
        storage.getTrainersData().put(trainer.getTrainerId(), trainer);
    }

    public boolean exists(Long userId) {
        return storage.getTrainersData().containsKey(userId);
    }

    public Trainer get(Long userId) {
        return storage.getTrainersData().get(userId);
    }

    public ArrayList<Trainer> getAll() {
        return new ArrayList<>(storage.getTrainersData().values());
    }

    public void set(Trainer trainer) {
        add(trainer);
    }

    public String toString(){
        return storage.getTrainersData().toString();
    }
}
