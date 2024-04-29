package com.example.taskspring.dto.loginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class ChangePasswordDTO {
    @NonNull
    private String username;
    @NonNull
    private String oldPassword;
    @NonNull
    private String newPassword;
}
