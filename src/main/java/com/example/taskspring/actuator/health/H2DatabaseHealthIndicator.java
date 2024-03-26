package com.example.taskspring.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Profile({"local"})
public class H2DatabaseHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
            connection.close();
            return Health.up().build();

        } catch (SQLException e) {
            return Health.up().build();
        }
    }
}
