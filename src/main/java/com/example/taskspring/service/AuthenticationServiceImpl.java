package com.example.taskspring.service;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@Service
@Slf4j
@NoArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private TraineesRepository traineesRepository;
    private TrainersRepository trainersRepository;

    @Autowired
    public AuthenticationServiceImpl(TraineesRepository traineesRepository, TrainersRepository trainersRepository) {
        this.traineesRepository = traineesRepository;
        this.trainersRepository = trainersRepository;
    }

    public void authenticate(String username, String password) throws AuthenticationException {
        Trainee trainee = traineesRepository.findByUsername(username).orElse(new Trainee());
        Trainer trainer = trainersRepository.findByUsername(username).orElse(new Trainer());
        boolean result = (trainee.getUsername() != null && trainee.getUsername().equals(username) &&
                trainee.getPassword().equals(password)) ||
                (trainer.getUsername() != null && trainer.getUsername().equals(username) && trainer.getPassword().equals(password));
        if(!result) throw new AuthenticationException("Invalid username or password");
    }

}
