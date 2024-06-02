package com.example.taskspring.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class TrainerSummary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "trainerSummary", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<YearlyTrainingSummary> yearlyTrainingSummaries;

    public TrainerSummary(String firstName, String lastName, String userName, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.isActive = isActive;
    }
}
