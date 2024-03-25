package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingMetrics{

    private final Counter totalTrainingsCreated;
    private final Counter totalTrainingsCreatedFailed;


    @Autowired
    public TrainingMetrics(MeterRegistry meterRegistry) {
        this.totalTrainingsCreated  = Counter.builder("Trainings created counter")
                .description("Number of trainings created successfully")
                .register(meterRegistry);
        this.totalTrainingsCreatedFailed  = Counter.builder("Trainings created counter failed")
                .description("Number of trainings create failed")
                .register(meterRegistry);
    }

    public void incrementTrainingsCreatedCounter() {
        totalTrainingsCreated.increment();
    }
    public void incrementTrainingsCreatedFailedCounter() {
        totalTrainingsCreatedFailed.increment();
    }

}