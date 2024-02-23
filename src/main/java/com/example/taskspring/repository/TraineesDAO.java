package repository;

import model.Trainee;

import java.util.ArrayList;

public interface TraineesDAO {
    public void add(Trainee trainee);

    public boolean exists(String userId);

    public void remove(String userId);

    public Trainee get(String userId);

    public ArrayList<Trainee> getAll();

    public void set(Trainee trainee);
}
