package repository;

import model.Trainer;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
@Repository
public class TrainersInMemoryDAO implements TrainersDAO{
    private final HashMap<String, Trainer> map = new HashMap<>();
    public void add(Trainer trainer) {
        map.put(trainer.getUserId(), trainer);
    }

    public boolean exists(String userId) {
        return map.containsKey(userId);
    }

    public Trainer get(String userId) {
        return map.get(userId);
    }

    public ArrayList<Trainer> getAll() {
        return new ArrayList<>(map.values());
    }

    public void set(Trainer trainer) {
        add(trainer);
    }

    public String toString(){
        return map.toString();
    }
}
