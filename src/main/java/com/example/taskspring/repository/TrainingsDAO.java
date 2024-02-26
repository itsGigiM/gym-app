package com.example.taskspring.repository;

import com.example.taskspring.model.Training;

import java.util.ArrayList;

public interface TrainingsDAO {
    public void add(Training training);
    public boolean exists(Long trainingId);

    public Training get(Long trainingId);

    public ArrayList<Training> getAll();
}
