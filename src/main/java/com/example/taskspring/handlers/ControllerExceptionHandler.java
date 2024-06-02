package com.example.taskspring.handlers;

import com.example.taskspring.actuator.metric.LoginMetrics;
import com.example.taskspring.actuator.metric.TraineeMetrics;
import com.example.taskspring.actuator.metric.TrainerMetrics;
import com.example.taskspring.actuator.metric.TrainingMetrics;
import com.example.taskspring.utils.Endpoint;
import com.example.taskspring.utils.RequestContext;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

    private RequestContext requestContext;
    private LoginMetrics loginMetrics;
    private TraineeMetrics traineeMetrics;
    private TrainerMetrics trainerMetrics;
    private TrainingMetrics trainingMetrics;

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleMalformedJwtException() {
        log.error("Invalid JWT");
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleNullPointerException(Exception ex) {
        log.error("One of the required parameter is null");
        Endpoint endpoint = requestContext.getEndpoint();
        updateMetrics(endpoint);
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleEntityNotFoundException(Exception ex) {
        log.error("Trainee/Trainer does not exists");
        Endpoint endpoint = requestContext.getEndpoint();
        updateMetrics(endpoint);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleAuthenticationException(Exception ex) {
        log.error("Wrong username or password");
        Endpoint endpoint = requestContext.getEndpoint();
        updateMetrics(endpoint);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(SecurityException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleSecurityException(Exception ex) {
        log.error("You attempted too much wrong credentials. Try again in 5 minutes");
        Endpoint endpoint = requestContext.getEndpoint();
        updateMetrics(endpoint);
        return new ResponseEntity<>(HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseBody
    public ResponseEntity<HttpStatus> handleIllegalStateException(Exception ex) {
        log.error("Token can not be found");
        Endpoint endpoint = requestContext.getEndpoint();
        updateMetrics(endpoint);
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }


    private void updateMetrics(Endpoint endpoint) {
        switch (endpoint) {
            case LOGIN:
                loginMetrics.incrementFailedCounter();
                break;
            case CHANGE_PASSWORD:
                loginMetrics.incrementFailedPasswordChangeCounter();
                break;
            case REGISTER_TRAINEE:
                traineeMetrics.incrementCreateTraineeFailed();
                break;
            case RETRIEVE_TRAINEE:
                traineeMetrics.incrementGetTraineeFailed();
                break;
            case MODIFY_TRAINEE:
                traineeMetrics.incrementUpdateTraineeFailed();
                break;
            case REMOVE_TRAINEE:
                traineeMetrics.incrementDeleteTraineeFailed();
                break;
            case UPDATE_TRAINER_LIST:
                traineeMetrics.incrementUpdateTraineeTrainerListFailed();
                break;
            case TRAINEE_RETRIEVE_TRAININGS_LIST:
                traineeMetrics.incrementGetTrainingListFailed();
                break;
            case TRAINEE_MODIFY_ACTIVE_STATUS:
                traineeMetrics.incrementPatchTraineeFailed();
                break;
            case RETRIEVE_UNASSIGNED_TRAINERS:
                traineeMetrics.incrementUnassignedTrainerFailed();
                break;
            case CREATE_TRAINING:
                trainingMetrics.incrementTrainingsCreatedFailedCounter();
                break;
            case REGISTER_TRAINER:
                trainerMetrics.incrementCreateTrainerFailedCounter();
                break;
            case RETRIEVE_TRAINER:
                trainerMetrics.incrementGetTrainerFailedCounter();
                break;
            case MODIFY_TRAINER:
                trainerMetrics.incrementUpdateTrainerFailedCounter();
                break;
            case TRAINER_RETRIEVE_TRAININGS_LIST:
                trainerMetrics.incrementGetTrainingListFailedCounter();
                break;
            case TRAINER_MODIFY_ACTIVE_STATUS:
                trainerMetrics.incrementPatchIsActiveFailedCounter();
                break;
            default:
                break;
        }
    }

}
