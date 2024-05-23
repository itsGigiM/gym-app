package com.example.taskspring.interfaces;

import com.example.taskspring.dto.trainerDTO.WorkHoursRetrieveDTO;
import com.example.taskspring.dto.trainingDTO.TrainerSessionWorkHoursUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient("totalDuration")
//@CircuitBreaker(name = "breaker")
public interface DurationServiceInterface {
    @PostMapping("/work-hours")
    ResponseEntity<HttpStatus> updateWorkHours(@RequestBody TrainerSessionWorkHoursUpdateDTO updateDto);
    @GetMapping("/work-hours/")
    ResponseEntity<WorkHoursRetrieveDTO> retrieveWorkHours(String username);
}
