package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
public class PostTraineeRequestDTO{
    private String firstName;

    private String lastName;

    private String address;

    private LocalDate dateOfBirth;
}
