package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExceptionHandlerMetrics {

    private final Counter nullPointerExceptionCounter;
    private final Counter entityNotFoundExceptionCounter;
    private final Counter authenticationExceptionCounter;

    @Autowired
    public ExceptionHandlerMetrics(MeterRegistry meterRegistry) {
        this.nullPointerExceptionCounter = Counter.builder("Null pointer exceptions handled")
                .description("Total number of NullPointerExceptions handled")
                .register(meterRegistry);
        this.entityNotFoundExceptionCounter = Counter.builder("Entity not found exceptions handled")
                .description("Total number of EntityNotFoundExceptions handled")
                .register(meterRegistry);
        this.authenticationExceptionCounter = Counter.builder("Authentication exceptions handled")
                .description("Total number of AuthenticationExceptions handled")
                .register(meterRegistry);
    }

    public void incrementNullPointerException() {
        nullPointerExceptionCounter.increment();
    }

    public void incrementEntityNotFoundException() {
        entityNotFoundExceptionCounter.increment();
    }

    public void incrementAuthenticationException() {
        authenticationExceptionCounter.increment();
    }
}
