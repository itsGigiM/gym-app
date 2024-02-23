package repository;

import model.Trainee;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
@Repository
public class TraineesInMemoryDAO implements TraineesDAO {
    private final HashMap<String, Trainee> map = new HashMap<>();
    public void add(Trainee trainee) {
        map.put(trainee.getUserId(), trainee);
    }

    public boolean exists(String userId) {
        return map.containsKey(userId);
    }

    public void remove(String userId) {
        map.remove(userId);
    }

    public Trainee get(String userId) {
        return map.get(userId);
    }

    public ArrayList<Trainee> getAll() {
        return new ArrayList<>(map.values());
    }

    public void set(Trainee trainee) {
        add(trainee);
    }

    public String toString(){
        return map.toString();
    }
}
