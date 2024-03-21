package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingMetrics{

    private final Counter totalTrainingsCreated;


    @Autowired
    public TrainingMetrics(MeterRegistry meterRegistry) {
        this.totalTrainingsCreated  = Counter.builder("Trainings created counter")
                .description("Number of trainings created successfully")
                .register(meterRegistry);
    }

    public void incrementTrainingsCreatedCounter() {
        totalTrainingsCreated.increment();
    }

}