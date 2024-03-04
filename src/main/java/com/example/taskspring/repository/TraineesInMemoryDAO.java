package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@AllArgsConstructor
public class TraineesInMemoryDAO implements TraineesDAO {
    private InMemoryStorage storage;
    public Trainee add(Trainee trainee) {
        storage.getTraineesData().put(trainee.getTraineeId(), trainee);
        return trainee;
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

    public List<Trainee> getAll() {
        return new ArrayList<>(storage.getTraineesData().values());
    }

    public Trainee set(Trainee trainee) {
        return add(trainee);
    }

    public String toString(){
        return storage.getTraineesData().toString();
    }
}
