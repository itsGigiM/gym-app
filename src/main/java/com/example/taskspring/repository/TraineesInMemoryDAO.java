package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.utils.InMemoryStorage;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
@Repository
@AllArgsConstructor
public class TraineesInMemoryDAO implements TraineesDAO {
    private InMemoryStorage storage;
    public void add(Trainee trainee) {
        storage.getTraineesData().put(trainee.getTraineeId(), trainee);
    }

    public boolean exists(String userId) {
        return storage.getTraineesData().containsKey(userId);
    }

    public void remove(String userId) {
        storage.getTraineesData().remove(userId);
    }

    public Trainee get(String userId) {
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
