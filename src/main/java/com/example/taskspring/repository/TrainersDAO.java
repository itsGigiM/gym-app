package com.example.taskspring.repository;

import com.example.taskspring.model.Trainer;

import java.util.List;

public interface TrainersDAO {
    public Trainer add(Trainer trainer);

    public boolean exists(Long userId);

    public Trainer get(Long userId);

    public List<Trainer> getAll();

    public Trainer set(Trainer trainer);
}