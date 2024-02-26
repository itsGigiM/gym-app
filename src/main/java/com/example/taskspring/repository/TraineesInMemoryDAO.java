package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.utils.InMemoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
@Repository
@AllArgsConstructor
public class TraineesInMemoryDAO implements TraineesDAO {
    private InMemoryStorage storage;
    public void add(Trainee trainee) {
        storage.getTraineesData().put(trainee.getTraineeId(), trainee);
    }

    public boolean exists(Long userId) {
        return storage.getTraineesData().containsKey(userId);
    }

    public void remove(Long userId) {
        storage.getTraineesData().remove(userId);
    }

    public Trainee get(Long userId) {
        return storage.getTraineesData().get(userId);
    }

    public ArrayList<Trainee> getAll() {
        return new ArrayList<>(storage.getTraineesData().values());
    }

    public void set(Trainee trainee) {
        add(trainee);
    }

    public String toString(){
        return storage.getTraineesData().toString();
    }
}
