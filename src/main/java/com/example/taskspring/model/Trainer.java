package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trainer{
    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private TrainingType specialization;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trainerId;

    @OneToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany
    @JoinTable(
            name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainer_id"),
            inverseJoinColumns = @JoinColumn(name = "trainee_id")
    )
    private Set<Trainee> trainees = new HashSet<>();

    public Trainer(String firstName, String lastName, String username,
                   String password, boolean isActive, TrainingType specialization) {
        this.user = new User(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
    }

    public Trainer(Long trainerId, String firstName, String lastName, String username,
                   String password, boolean isActive, TrainingType specialization) {
        this.user = new User(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.trainerId = trainerId;
    }

    public Trainer(User user, TrainingType specialization) {
        this.user = user;
        this.specialization = specialization;
    }

    public String toString(){
        return  "Trainee{" + user.toString() +
                "specialization='" + specialization + '\'' +
                ", trainerId='" + trainerId + '\'' +
                "} ";
    }
}
