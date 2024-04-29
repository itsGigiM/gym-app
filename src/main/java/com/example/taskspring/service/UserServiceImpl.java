package com.example.taskspring.service;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.User;
import com.example.taskspring.model.UserDetailsModel;
import com.example.taskspring.repository.repositories.TraineesRepository;
import com.example.taskspring.repository.repositories.TrainersRepository;
import com.example.taskspring.repository.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@NoArgsConstructor
@Slf4j
public class UserServiceImpl implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepo) {
        this.userRepository = userRepo;
    }
    @Override
    public UserDetails loadUserByUsername(String username){
        String errorMessage = "User not found with username: " + username;
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(errorMessage));
        return new UserDetailsModel(user);
    }
}
