package com.example.taskspring.dto.traineeDTO;

import com.example.taskspring.model.Trainer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
@AllArgsConstructor
public class UpdateTraineeTrainerListRequestDTO {
    @NonNull
    private String username;
    @NonNull
    private Set<String> trainersList;
}
