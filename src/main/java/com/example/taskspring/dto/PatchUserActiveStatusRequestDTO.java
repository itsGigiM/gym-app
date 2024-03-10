package com.example.taskspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class PatchUserActiveStatusRequestDTO {
    @NonNull
    private String username;
    private boolean isActive;
}
