package com.example.taskspring.repository;

import com.example.taskspring.model.Training;
import java.util.List;

public interface TrainingsDAO {
    public Training add(Training training);
    public boolean exists(Long trainingId);

    public Training get(Long trainingId);

    public List<Training> getAll();
}
