package com.example.taskspring.model;

import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
public class Trainer extends User{
    private String specialization;
    private Long trainerId;
    public Trainer(String firstName, String lastName, String username, String password, boolean isActive,
                   String specialization, Long trainerId) {
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
