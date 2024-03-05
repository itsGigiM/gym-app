package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UsernameGeneratorImpl implements UsernameGenerator {

    TraineesRepository traineesRepository;
    TrainersRepository trainersRepository;

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
        return traineesRepository.findByUserUsername(username).isPresent() ||
                trainersRepository.findByUserUsername(username).isPresent();
    }

}
