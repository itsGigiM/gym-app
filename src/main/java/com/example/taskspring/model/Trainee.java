package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trainee extends User{

    private Long traineeId;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "trainees")
    private Set<Trainer> trainers = new HashSet<>();

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
