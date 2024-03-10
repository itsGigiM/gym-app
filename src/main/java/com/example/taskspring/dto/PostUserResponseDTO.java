package com.example.taskspring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostUserResponseDTO {
    private String username;
    private String password;
}
