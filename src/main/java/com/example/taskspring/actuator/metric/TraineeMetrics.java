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

    private final Counter createTraineeFailedCounter;
    private final Counter getTraineeFailedCounter;
    private final Counter updateTraineeFailedCounter;
    private final Counter deleteTraineeFailedCounter;
    private final Counter patchTraineeFailedCounter;
    private final Counter getTrainingListFailedCounter;
    private final Counter getUnassignedTrainersFailedCounter;

    private final Counter updateTraineeTrainerListFailedCounter;


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

        this.createTraineeFailedCounter = Counter.builder("trainee create failed")
                .description("Total number of failed trainee creation operations")
                .register(meterRegistry);
        this.getTraineeFailedCounter = Counter.builder("trainee retrieve failed")
                .description("Total number of failed trainee retrieval operations")
                .register(meterRegistry);
        this.patchTraineeFailedCounter = Counter.builder("trainee patch failed")
                .description("Total number of failed trainee patch operations")
                .register(meterRegistry);
        this.updateTraineeFailedCounter = Counter.builder("trainee update failed")
                .description("Total number of failed trainee update operations")
                .register(meterRegistry);
        this.deleteTraineeFailedCounter = Counter.builder("trainee delete failed")
                .description("Total number of failed trainee deletion operations")
                .register(meterRegistry);
        this.getTrainingListFailedCounter = Counter.builder("training list retrieve failed")
                .description("Total number of failed training list retrieve operations")
                .register(meterRegistry);
        this.getUnassignedTrainersFailedCounter = Counter.builder("Unassigned trainers list retrieve failed")
                .description("Total number of failed unassigned trainers list retrieve operations")
                .register(meterRegistry);
        this.updateTraineeTrainerListFailedCounter = Counter.builder("trainee trainers list retrieve failed")
                .description("Total number of failed trainers list retrieve operations")
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

    public void incrementCreateTraineeFailed() {createTraineeFailedCounter.increment();}

    public void incrementGetTraineeFailed() {
        getTraineeFailedCounter.increment();
    }

    public void incrementGetTrainingListFailed() {
        getTrainingListFailedCounter.increment();
    }

    public void incrementUpdateTraineeFailed() {
        updateTraineeFailedCounter.increment();
    }

    public void incrementDeleteTraineeFailed() {
        deleteTraineeFailedCounter.increment();
    }
    public void incrementPatchTraineeFailed() {
        patchTraineeFailedCounter.increment();
    }
    public void incrementUnassignedTrainerFailed() {
        getUnassignedTrainersFailedCounter.increment();
    }

    public void incrementUpdateTraineeTrainerListFailed() {updateTraineeTrainerListFailedCounter.increment();}
}
