package com.example.taskspring.repository;

import com.example.taskspring.model.Trainee;

import java.util.ArrayList;

public interface TraineesDAO {
    public void add(Trainee trainee);

    public boolean exists(Long userId);

    public void remove(Long userId);

    public Trainee get(Long userId);

    public ArrayList<Trainee> getAll();

    public void set(Trainee trainee);
}
