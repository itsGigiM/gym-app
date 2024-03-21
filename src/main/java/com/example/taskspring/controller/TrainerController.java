package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.trainerDTO.*;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.naming.AuthenticationException;

public interface TrainerController {
    @Operation(summary = "Register trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered trainer")})
    ResponseEntity<AuthenticationDTO> create(PostTrainerRequestDTO postTrainerRequestDTO);

    @Operation(summary = "Retrieve trainer by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainer with this username")})
    ResponseEntity<GetTrainerResponseDTO> get(String username, String user, String password) throws AuthenticationException;

    @Operation(summary = "Modify trainer by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified trainer"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainer with this username")})
    ResponseEntity<PutTrainerResponseDTO> put(String username, PutTrainerRequestDTO putTrainerRequestDTO,
                                              String user, String password) throws AuthenticationException;

    @Operation(summary = "Retrieve trainings list by trainer's username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainer with this username")})
    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTrainerTrainingListRequestDTO request,
                                                                   String username, String password) throws AuthenticationException;

    @Operation(summary = "Modify activity status of a trainer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified activity status of a trainer"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainer with this username")})
    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request, String username, String password) throws AuthenticationException;
}
