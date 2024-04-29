package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TrainingTypeMetrics {

    private final Counter getAllCounter;

    @Autowired
    public TrainingTypeMetrics(MeterRegistry meterRegistry) {
        this.getAllCounter = Counter.builder("TrainingType get_all counter")
                .description("Counts the number of getAll TrainingType requests")
                .register(meterRegistry);
    }

    public void incrementGetAllCounter() {
        getAllCounter.increment();
    }
}