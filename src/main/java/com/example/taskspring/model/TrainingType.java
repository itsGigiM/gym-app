package com.example.taskspring.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "training_types")
@AllArgsConstructor
@NoArgsConstructor
public class TrainingType {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    private TrainingTypeEnum trainingTypeName;
}
