package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainerMetrics {

    private final Counter createTrainerSuccessCounter;
    private final Counter createTrainerFailedCounter;
    private final Counter getTrainerSuccessCounter;
    private final Counter getTrainerFailedCounter;
    private final Counter updateTrainerSuccessCounter;
    private final Counter updateTrainerFailedCounter;
    private final Counter getTrainingListSuccessCounter;
    private final Counter getTrainingListFailedCounter;
    private final Counter patchIsActiveSuccessCounter;
    private final Counter patchIsActiveFailedCounter;

    @Autowired
    public TrainerMetrics(MeterRegistry meterRegistry) {
        this.createTrainerSuccessCounter = Counter.builder("Created trainers successfully")
                .description("Total number of create trainer requests")
                .register(meterRegistry);
        this.createTrainerFailedCounter = Counter.builder("Failed to create trainers")
                .description("Total number of failed create trainer requests")
                .register(meterRegistry);
        this.getTrainerSuccessCounter = Counter.builder("Retrieved trainers successfully")
                .description("Total number of get trainer requests")
                .register(meterRegistry);
        this.getTrainerFailedCounter = Counter.builder("Failed to retrieve trainers")
                .description("Total number of failed get trainer requests")
                .register(meterRegistry);
        this.updateTrainerSuccessCounter = Counter.builder("Updated trainers successfully")
                .description("Total number of update trainer requests")
                .register(meterRegistry);
        this.updateTrainerFailedCounter = Counter.builder("Failed to update trainers")
                .description("Total number of failed update trainer requests")
                .register(meterRegistry);
        this.getTrainingListSuccessCounter = Counter.builder("Retrieve training list successfully")
                .description("Total number of get training list requests")
                .register(meterRegistry);
        this.getTrainingListFailedCounter = Counter.builder("Failed to retrieve training list")
                .description("Total number of failed get training list requests")
                .register(meterRegistry);
        this.patchIsActiveSuccessCounter = Counter.builder("Patched is_active successfully")
                .description("Total number of patch is active requests")
                .register(meterRegistry);
        this.patchIsActiveFailedCounter = Counter.builder("Failed to patch is_active")
                .description("Total number of failed patch is active requests")
                .register(meterRegistry);
    }

    public void incrementCreateTrainerSuccessCounter() {
        createTrainerSuccessCounter.increment();
    }

    public void incrementCreateTrainerFailedCounter() {
        createTrainerFailedCounter.increment();
    }

    public void incrementGetTrainerSuccessCounter() {
        getTrainerSuccessCounter.increment();
    }

    public void incrementGetTrainerFailedCounter() {
        getTrainerFailedCounter.increment();
    }

    public void incrementUpdateTrainerSuccessCounter() {
        updateTrainerSuccessCounter.increment();
    }

    public void incrementUpdateTrainerFailedCounter() {
        updateTrainerFailedCounter.increment();
    }

    public void incrementGetTrainingListSuccessCounter() {
        getTrainingListSuccessCounter.increment();
    }

    public void incrementGetTrainingListFailedCounter() {
        getTrainingListFailedCounter.increment();
    }

    public void incrementPatchIsActiveSuccessCounter() {
        patchIsActiveSuccessCounter.increment();
    }

    public void incrementPatchIsActiveFailedCounter() {
        patchIsActiveFailedCounter.increment();
    }
}
