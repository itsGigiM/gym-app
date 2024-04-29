package com.example.taskspring.dto.loginDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
public class AuthenticationDTO {
    @NonNull
    private String username;

    @NonNull
    private String password;
}
