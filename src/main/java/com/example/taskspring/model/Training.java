package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "trainings")
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long trainingId;
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @Column(nullable = false)
    private String trainingName;
    @Enumerated(EnumType.STRING)
    @Column(name = "training_type")
    private TrainingType.TrainingTypeEnum trainingType;
    @Column(nullable = false)
    private LocalDate trainingDate;
    @Column(nullable = false)
    private Duration duration;

}
