package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TraineesDAO;
import com.example.taskspring.repository.TrainersDAO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernameGeneratorImpl implements UsernameGenerator {

    TraineesDAO traineesDAO;
    TrainersDAO trainersDAO;

    public String generateUsername(String firstName, String lastName) {
        String username = firstName + "." + lastName;
        int n = 1;
        while(storageContainsUsername(username)){
            username = firstName + "." + lastName + n;
            n++;
        }
        return username;
    }

    private boolean storageContainsUsername(String username) {
        for(Trainee t : traineesDAO.getAll()){
            if(username.equals(t.getUsername())){
                return true;
            }
        }
        for(Trainer t : trainersDAO.getAll()){
            if(username.equals(t.getUsername())){
                return true;
            }
        }
        return false;
    }

}
