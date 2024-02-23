package com.example.taskspring.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Trainee extends User{
    private String traineeId;
    private String address;
    private LocalDate dateOfBirth;
    public Trainee(String firstName, String lastName, String username,
                   String password, boolean isActive, String traineeId, String address, LocalDate dateOfBirth) {
        super(firstName, lastName, username, password, isActive);
        this.address = address;
        this.traineeId = traineeId;
        this.dateOfBirth = dateOfBirth;
    }

    public String toString(){
        return  "Trainee{" + super.toString() +
                "traineeId='" + traineeId + '\'' +
                ", address='" + address + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                "} ";
    }
}
