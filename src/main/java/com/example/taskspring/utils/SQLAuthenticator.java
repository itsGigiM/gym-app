package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.TraineesRepository;
import com.example.taskspring.repository.TrainersRepository;
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
        Trainee trainee = traineesRepository.findByUserUsername(username).orElse(new Trainee());
        Trainer trainer = trainersRepository.findByUserUsername(username).orElse(new Trainer());
        boolean result = (trainee.getUser().getUsername() != null && trainee.getUser().getUsername().equals(username) &&
                trainee.getUser().getPassword().equals(password)) ||
                (trainer.getUser().getUsername() != null && trainer.getUser().getUsername().equals(username) &&
                        trainer.getUser().getPassword().equals(password));
        if(!result) throw new AuthenticationException("Invalid username or password");
    }
}
