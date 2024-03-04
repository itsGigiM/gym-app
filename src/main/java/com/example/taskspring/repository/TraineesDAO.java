package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;
import java.util.List;

public interface TraineesDAO {
    public Trainee add(Trainee trainee);

    public boolean exists(Long userId);

    public void remove(Long userId);

    public Trainee get(Long userId);

    public List<Trainee> getAll();

    public Trainee set(Trainee trainee);
}
