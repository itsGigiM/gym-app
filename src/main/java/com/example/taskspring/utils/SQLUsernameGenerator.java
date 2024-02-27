package com.example.taskspring.utils;

import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@AllArgsConstructor
public class SQLUsernameGenerator implements IUsernameGenerator{
    TraineesRepository traineesRepository;
    TrainersRepository trainersRepository;

    public String generateUsername(String firstName, String lastName) {
        String username = firstName + "." + lastName;
        int n = 1;
        while(trainersRepository.findByUsername(username).isPresent() ||
                traineesRepository.findByUsername(username).isPresent()
        ){
            username = firstName + "." + lastName + n;
            n++;
        }
        return username;
    }
}
