package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;

@AllArgsConstructor
@Component
public class SQLAuthenticator implements Authenticator{
    private TraineesRepository traineesRepository;
    private TrainersRepository trainersRepository;
    @Override
    public void authenticate(String username, String password) throws AuthenticationException {
        Trainee trainee = traineesRepository.findByUsername(username).orElse(new Trainee());
        Trainer trainer = trainersRepository.findByUsername(username).orElse(new Trainer());
        boolean result = (trainee.getUsername() != null && trainee.getUsername().equals(username) &&
                trainee.getPassword().equals(password)) ||
                (trainer.getUsername() != null && trainer.getUsername().equals(username) && trainer.getPassword().equals(password));
        if(!result) throw new AuthenticationException("Invalid username or password");
    }
}
