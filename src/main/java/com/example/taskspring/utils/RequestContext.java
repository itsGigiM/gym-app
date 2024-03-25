package com.example.taskspring.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.RequestScope;

@Data
@Component
@RequestScope
public class RequestContext {
    private Endpoint endpoint;

    public RequestContext(HttpServletRequest request) {
        endpoint = determineEndpoint(request);
    }

    private Endpoint determineEndpoint(HttpServletRequest request) {
        String requestMethod = String.valueOf(RequestMethod.valueOf(request.getMethod()));
        String uri = request.getRequestURI();
        switch (requestMethod) {
            case "POST" -> {
                if (uri.contains("/trainers/")) {
                    return Endpoint.REGISTER_TRAINER;
                } else if (uri.contains("/trainees/")) {
                    return Endpoint.REGISTER_TRAINER;
                } else if (uri.contains("/trainings/")) {
                    return Endpoint.CREATE_TRAINING;
                }
            }
            case "GET" -> {
                if (uri.contains("/trainers/training-list")) {
                    return Endpoint.TRAINER_RETRIEVE_TRAININGS_LIST;
                } else if (uri.contains("/trainees/training-list")) {
                    return Endpoint.TRAINEE_RETRIEVE_TRAININGS_LIST;
                } else if (uri.contains("/trainees/unassigned-trainers")) {
                    return Endpoint.RETRIEVE_UNASSIGNED_TRAINERS;
                } else if (uri.contains("/trainers/")) {
                    return Endpoint.RETRIEVE_TRAINER;
                } else if (uri.contains("/trainees/")) {
                    return Endpoint.RETRIEVE_TRAINEE;
                } else if (uri.contains("/login")) {
                    return Endpoint.LOGIN;
                } else if (uri.contains("/training-types")) {
                    return Endpoint.TRAINING_TYPES;
                }
            }
            case "PUT" -> {
                if (uri.contains("/trainers")) {
                    return Endpoint.MODIFY_TRAINER;
                } else if (uri.contains("/trainees")) {
                    return Endpoint.MODIFY_TRAINEE;
                } else if (uri.contains("/trainees/trainer-list")) {
                    return Endpoint.UPDATE_TRAINER_LIST;
                } else if (uri.contains("/login")) {
                    return Endpoint.CHANGE_PASSWORD;
                }
            }
            case "PATCH" -> {
                if (uri.contains("/trainers")) {
                    return Endpoint.TRAINER_MODIFY_ACTIVE_STATUS;
                } else if (uri.contains("/trainees")) {
                    return Endpoint.TRAINEE_MODIFY_ACTIVE_STATUS;
                }
            }
            case "DELETE" -> {
                    return Endpoint.REMOVE_TRAINEE;
            }
        }
        return null;
    }
}

