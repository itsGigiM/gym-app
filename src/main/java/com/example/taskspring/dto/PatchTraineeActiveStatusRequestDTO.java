package com.example.taskspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PatchTraineeActiveStatusRequestDTO {
    private String username;
    private boolean isActive;
}
