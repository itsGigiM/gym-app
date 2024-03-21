package com.example.taskspring.controller;

import com.example.taskspring.dto.loginDTO.AuthenticationDTO;
import com.example.taskspring.dto.PatchUserActiveStatusRequestDTO;
import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.dto.trainerDTO.GetUnassignedTrainersDTO;
import com.example.taskspring.dto.trainingDTO.GetUserTrainingListResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.naming.AuthenticationException;

public interface TraineeController {


    @Operation(summary = "Register trainee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Trainee Registered")})
    ResponseEntity<AuthenticationDTO> create(PostTraineeRequestDTO postTraineeRequestDTO);

    @Operation(summary = "Retrieve user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    ResponseEntity<GetTraineeResponseDTO> get(String username, String user, String password) throws AuthenticationException;

    @Operation(summary = "Modify trainee by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified trainee"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    ResponseEntity<PutTraineeResponseDTO> put(String username, PutTraineeRequestDTO putTraineeRequestDTO, String user, String password) throws AuthenticationException;

    @Operation(summary = "Remove user by username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed trainer"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    ResponseEntity<HttpStatus> delete(String username, String user, String password) throws AuthenticationException;

    @Operation(summary = "Update trainee's trainers list")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the list"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    ResponseEntity<UpdateTraineeTrainerListResponseDTO> updateTrainerList(UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO,
                                                                          String username, String password) throws AuthenticationException;

    @Operation(summary = "Retrieve trainings list by trainee's username")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    ResponseEntity<GetUserTrainingListResponseDTO> getTrainingList(GetTraineeTrainingListRequestDTO request, String username, String password) throws AuthenticationException;

    @Operation(summary = "Modify active status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully modified active status"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    ResponseEntity<HttpStatus> updateIsActive(PatchUserActiveStatusRequestDTO request, String username, String password) throws AuthenticationException;

    @Operation(summary = "Retrieve Unassigned trainers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Wrong user or password provided"),
            @ApiResponse(responseCode = "404", description = "No trainee with this username")})
    public ResponseEntity<GetUnassignedTrainersDTO> getUnassignedTrainers(String username, String user, String password) throws AuthenticationException;

}
