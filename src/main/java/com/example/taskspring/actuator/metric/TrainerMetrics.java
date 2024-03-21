package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerMetrics {

    private final Counter createTrainerCounter;
    private final Counter getTrainerCounter;
    private final Counter updateTrainerCounter;
    private final Counter getTrainingListCounter;
    private final Counter patchIsActiveCounter;

    @Autowired
    public TrainerMetrics(MeterRegistry meterRegistry) {
        this.createTrainerCounter = Counter.builder("Created trainers successfully")
                .description("Total number of create trainer requests")
                .register(meterRegistry);
        this.getTrainerCounter = Counter.builder("Retrieved trainers successfully")
                .description("Total number of get trainer requests")
                .register(meterRegistry);
        this.updateTrainerCounter = Counter.builder("Updated trainers successfully")
                .description("Total number of update trainer requests")
                .register(meterRegistry);
        this.getTrainingListCounter = Counter.builder("Retrieve training list successfully")
                .description("Total number of get training list requests")
                .register(meterRegistry);
        this.patchIsActiveCounter = Counter.builder("Patched is_active successfully")
                .description("Total number of patch is active requests")
                .register(meterRegistry);
    }

    public void incrementCreateTrainerCounter() {
        createTrainerCounter.increment();
    }

    public void incrementGetTrainerCounter() {
        getTrainerCounter.increment();
    }

    public void incrementUpdateTrainerCounter() {
        updateTrainerCounter.increment();
    }

    public void incrementGetTrainingListCounter() {
        getTrainingListCounter.increment();
    }

    public void incrementPatchIsActiveCounter() {
        patchIsActiveCounter.increment();
    }
}
