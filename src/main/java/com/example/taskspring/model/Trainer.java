package com.example.taskspring.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Trainer extends User{
    private TrainingType specialization;
    private Long trainerId;
    public Trainer(String firstName, String lastName, String username, String password, boolean isActive,
                   TrainingType specialization, Long trainerId) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.trainerId = trainerId;
    }

    public String toString(){
        return  "Trainee{" + super.toString() +
                "specialization='" + specialization + '\'' +
                ", trainerId='" + trainerId + '\'' +
                "} ";
    }
}
