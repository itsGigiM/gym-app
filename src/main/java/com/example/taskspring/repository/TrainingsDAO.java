package repository;

import model.Training;

import java.util.ArrayList;

public interface TrainingsDAO {
    public void add(Training trainee);
    public boolean exists(String trainingId);

    public Training get(String trainingId);

    public ArrayList<Training> getAll();
}
