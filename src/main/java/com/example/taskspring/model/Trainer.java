package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
public class Trainer extends User{
    @Enumerated(EnumType.STRING)
    @Column(name = "specialization")
    private TrainingType.TrainingTypeEnum specialization;


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

    public Trainer(String firstName, String lastName, String username, String password, boolean isActive,
                   TrainingType.TrainingTypeEnum specialization, Long trainerId) {
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
