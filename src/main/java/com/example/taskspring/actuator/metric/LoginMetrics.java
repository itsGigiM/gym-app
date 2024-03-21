package com.example.taskspring.actuator.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoginMetrics{

    private final Counter totalSuccessfulLoginsCounter;
    private final Counter totalPasswordChangeCounter;

    @Autowired
    public LoginMetrics(MeterRegistry meterRegistry) {
        this.totalSuccessfulLoginsCounter  = Counter.builder("Total Successful logins")
                .register(meterRegistry);
        this.totalPasswordChangeCounter  = Counter.builder("Total passwords changed")
                .register(meterRegistry);
    }

    public void incrementSuccessfulCounter() {
        totalSuccessfulLoginsCounter.increment();
    }

    public void incrementPasswordChangeCounter () {
        totalPasswordChangeCounter.increment();
    }

}