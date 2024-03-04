package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "trainings")
@AllArgsConstructor
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainingId;
    @ManyToOne
    @JoinColumn(name = "trainee_id")
    private Trainee trainee;
    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
    @Column(nullable = false)
    private String trainingName;
    @ManyToOne
    @JoinColumn(name = "training_type_id", nullable = false)
    private TrainingType trainingType;
    @Column(nullable = false)
    private LocalDate trainingDate;
    @Column(nullable = false)
    private Duration duration;

    public Training(Trainee trainee, Trainer trainer, String trainingName,
                    TrainingType trainingType,
                    LocalDate trainingDate, Duration duration) {
        this.trainee = trainee;
        this.trainer = trainer;
        this.trainingName = trainingName;
        this.trainingType = trainingType;
        this.trainingDate = trainingDate;
        this.duration = duration;
    }
}
