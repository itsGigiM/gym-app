package com.example.taskspring.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Trainer extends User{
    private String specialization;
    private String trainerId;
    public Trainer(String firstName, String lastName, String username, String password, boolean isActive,
                   String specialization, String trainerId) {
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
