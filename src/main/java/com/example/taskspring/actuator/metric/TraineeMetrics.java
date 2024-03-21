package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TraineeMetrics {

    private final Counter createTraineeCounter;
    private final Counter getTraineeCounter;
    private final Counter updateTraineeCounter;
    private final Counter deleteTraineeCounter;
    private final Counter patchTraineeCounter;

    private final Counter getTrainingListCounter;


    @Autowired
    public TraineeMetrics(MeterRegistry meterRegistry) {
        this.createTraineeCounter = Counter.builder("trainee created successfully")
                .description("Total number of successful trainee creation operations")
                .register(meterRegistry);
        this.getTraineeCounter = Counter.builder("trainee retrieved successfully")
                .description("Total number of successful trainee retrieval operations")
                .register(meterRegistry);
        this.patchTraineeCounter = Counter.builder("trainee patched successfully")
                .description("Total number of failed trainee retrieval operations")
                .register(meterRegistry);
        this.updateTraineeCounter = Counter.builder("trainee updated successfully")
                .description("Total number of successful trainee update operations")
                .register(meterRegistry);
        this.deleteTraineeCounter = Counter.builder("trainee deleted successfully")
                .description("Total number of successful trainee deletion operations")
                .register(meterRegistry);
        this.getTrainingListCounter = Counter.builder("training list retrieved successfully")
                .description("Total number of successful training list retrieve operations")
                .register(meterRegistry);
    }

    public void incrementCreateTrainee() {
        createTraineeCounter.increment();
    }

    public void incrementGetTrainee() {
        getTraineeCounter.increment();
    }

    public void incrementGetTrainingList() {
        getTrainingListCounter.increment();
    }

    public void incrementUpdateTrainee() {
        updateTraineeCounter.increment();
    }

    public void incrementDeleteTrainee() {
        deleteTraineeCounter.increment();
    }
    public void incrementPatchTrainee() {
        patchTraineeCounter.increment();
    }

}
