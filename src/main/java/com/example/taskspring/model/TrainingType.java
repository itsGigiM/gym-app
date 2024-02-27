package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "training_types")
public class TrainingType {
    @Enumerated(EnumType.STRING)
    @Id
    private TrainingTypeEnum typeName;

    public enum TrainingTypeEnum {
        CARDIO,
        JIUJITSU,
        BOXING,
        MMA
    }
}
