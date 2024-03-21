package com.example.taskspring.handlers;

import com.example.taskspring.actuator.metric.ExceptionHandlerMetrics;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;

@ControllerAdvice
@Slf4j
@AllArgsConstructor
public class ControllerExceptionHandler {

    private ExceptionHandlerMetrics exceptionHandlerMetrics;
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleNullPointerException(Exception ex) {
        log.error("One of the required parameter is null");
        exceptionHandlerMetrics.incrementNullPointerException();
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleEntityNotFoundException(Exception ex) {
        log.error("Trainee/Trainer does not exists");
        exceptionHandlerMetrics.incrementEntityNotFoundException();
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleAuthenticationException(Exception ex) {
        log.error("Wrong username or password");
        exceptionHandlerMetrics.incrementAuthenticationException();
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
