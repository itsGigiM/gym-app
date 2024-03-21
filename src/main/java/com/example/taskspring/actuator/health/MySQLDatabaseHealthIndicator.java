package com.example.taskspring.actuator.health;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Component
@Profile({"mysql"})
public class MySQLDatabaseHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cs108", "root", "freeuni2023");
            connection.close();
            return Health.up().build();
        } catch (SQLException e) {
            return Health.down(e).build();
        }
    }
}
