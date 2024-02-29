package com.example.taskspring.model;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class Trainee extends User{
    private Long traineeId;
    private String address;
    private LocalDate dateOfBirth;
    public Trainee(String firstName, String lastName, String username,
                   String password, boolean isActive, Long traineeId, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, username, password, isActive);
        this.address = address;
        this.traineeId = traineeId;
        this.dateOfBirth = dateOfBirth;
    }

    public String toString(){
        return  "Trainee{" + super.toString() +
                "traineeId='" + traineeId + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                "} ";
    }
}
