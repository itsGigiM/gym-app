package com.example.taskspring.utils;

import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@AllArgsConstructor
public class SQLUsernameGenerator implements UsernameGenerator {
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
        return traineesRepository.findByUsername(username).isPresent() ||
                trainersRepository.findByUsername(username).isPresent();
    }
}
