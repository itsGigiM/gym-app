package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.loginDTO.ChangePasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;

public interface LoginController {
    @Operation(summary = "Login by providing username and password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Welcome"),
            @ApiResponse(responseCode = "401", description = "Wrong username or password")})
    public ResponseEntity<HttpStatus> login(@RequestBody AuthenticationDTO request) throws AuthenticationException;

    @Operation(summary = "Replace old password with a new one")
    public ResponseEntity<HttpStatus> changePassword(@RequestBody ChangePasswordDTO request) throws AuthenticationException;
}
