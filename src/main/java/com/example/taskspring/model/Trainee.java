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
@PrimaryKeyJoinColumn(name = "user_id")
public class Trainee extends User{

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address")
    private String address;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToMany(mappedBy = "trainees")
    private Set<Trainer> trainers = new HashSet<>();

    @OneToMany(mappedBy = "trainee", cascade = CascadeType.REMOVE)
    private Set<Training> trainings = new HashSet<>();

    public Trainee(Long userId, String firstName, String lastName, String username,
                   String password, boolean isActive, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, username, password, isActive);
        this.address = address;
        this.dateOfBirth = dateOfBirth;
        this.userId = userId;
    }

    public Trainee(String firstName, String lastName, String username,
                   String password, boolean isActive, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, username, password, isActive);
        this.address = address;
        this.dateOfBirth = dateOfBirth;
    }

    public String toString(){
        return  "Trainee{" + super.toString() +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                "} ";
    }
}
