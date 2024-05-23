package com.example.taskspring.dto.trainingDTO;

import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainerSessionWorkHoursUpdateDTO {
    private String trainerUsername;
    private String firstName;
    private String lastName;
    private Boolean isActive;
    private LocalDate trainingDate;
    private Long trainingDuration;
    private String actionType;
}
