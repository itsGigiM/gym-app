package com.example.taskspring.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class YearlyTrainingSummary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer trainingYear;
    private Long januaryDuration;
    private Long februaryDuration;
    private Long marchDuration;
    private Long aprilDuration;
    private Long mayDuration;
    private Long juneDuration;
    private Long julyDuration;
    private Long augustDuration;
    private Long septemberDuration;
    private Long octoberDuration;
    private Long novemberDuration;
    private Long decemberDuration;

    @ManyToOne
    @JoinColumn(name = "trainer_user_id", nullable = false)
    @JsonBackReference
    private TrainerSummary trainerSummary;

    public YearlyTrainingSummary(Integer trainingYear, Long januaryDuration, Long februaryDuration,
                                 Long marchDuration, Long aprilDuration, Long mayDuration,
                                 Long juneDuration, Long julyDuration, Long augustDuration,
                                 Long septemberDuration, Long octoberDuration,
                                 Long novemberDuration, Long decemberDuration,
                                 TrainerSummary trainer) {
        this.trainingYear = trainingYear;
        this.januaryDuration = januaryDuration;
        this.februaryDuration = februaryDuration;
        this.marchDuration = marchDuration;
        this.aprilDuration = aprilDuration;
        this.mayDuration = mayDuration;
        this.juneDuration = juneDuration;
        this.julyDuration = julyDuration;
        this.augustDuration = augustDuration;
        this.septemberDuration = septemberDuration;
        this.octoberDuration = octoberDuration;
        this.novemberDuration = novemberDuration;
        this.decemberDuration = decemberDuration;
        this.trainerSummary = trainer;
    }
}