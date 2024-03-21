package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingDTO.PostTrainingRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.naming.AuthenticationException;

public interface TrainingController {
    @Operation(summary = "Register training")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Sucessfully registered"),
            @ApiResponse(responseCode = "404", description = "Trainee or trainer could not be found")})
    public ResponseEntity<HttpStatus> create(PostTrainingRequest request, String username,
                                             String password) throws AuthenticationException;
}
