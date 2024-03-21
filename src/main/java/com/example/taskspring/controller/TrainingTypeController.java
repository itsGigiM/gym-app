package com.example.taskspring.controller;

import com.example.taskspring.dto.trainingTypeDTO.GetTrainingTypesDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

public interface TrainingTypeController {
    @Operation(summary = "Retrieve all training types")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved")})
    public ResponseEntity<GetTrainingTypesDTO> getAll();
}
