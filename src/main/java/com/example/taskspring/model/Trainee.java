package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trainee{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long traineeId;
    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "trainees")
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.REMOVE)
    private Set<Training> trainings = new HashSet<>();

    public Trainee(Long traineeId, String firstName, String lastName, String username,
                   String password, boolean isActive, String address, LocalDate dateOfBirth) {
        this.user = new User(firstName, lastName, username, password, isActive);
        this.address = address;
        this.traineeId = traineeId;
        this.dateOfBirth = dateOfBirth;
    }

    public Trainee(String firstName, String lastName, String username,
                   String password, boolean isActive, String address, LocalDate dateOfBirth) {
        this.user = new User(firstName, lastName, username, password, isActive);
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public Trainee(User user, String address, LocalDate dateOfBirth) {
        this.user = user;
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public String toString(){
        return  "Trainee{" + user.toString() +
                "traineeId='" + traineeId + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                "} ";
    }
}
