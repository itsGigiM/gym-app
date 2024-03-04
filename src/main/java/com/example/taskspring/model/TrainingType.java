package com.example.taskspring.model;


import jakarta.persistence.*;
import lombok.Data;

@Table(name = "training_types")
@Entity
@Data
public class TrainingType {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int trainingTypeId;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrainingTypeEnum trainingType;

    public enum TrainingTypeEnum {
        BOXING, JIUJITSU, KARATE
    }
}