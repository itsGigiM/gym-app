package repository;

import model.Trainer;

import java.util.ArrayList;

public interface TrainersDAO {
    public void add(Trainer trainee);

    public boolean exists(String userId);

    public Trainer get(String userId);

    public ArrayList<Trainer> getAll();

    public void set(Trainer trainee);
}
