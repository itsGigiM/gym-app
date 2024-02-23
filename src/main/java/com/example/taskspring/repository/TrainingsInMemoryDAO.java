package repository;

import model.Training;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
@Repository
public class TrainingsInMemoryDAO implements TrainingsDAO{
    private final HashMap<String, Training> map = new HashMap<>();
    public void add(Training training) {
        map.put(training.getTrainingId(), training);
    }

    public boolean exists(String trainingId) { return map.containsKey(trainingId); }

    public Training get(String userId) {
        return map.get(userId);
    }

    public ArrayList<Training> getAll() {
        return new ArrayList<>(map.values());
    }

    public String toString(){
        return map.toString();
    }
}
