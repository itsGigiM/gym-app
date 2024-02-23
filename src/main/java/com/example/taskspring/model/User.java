package com.example.taskspring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class User {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean isActive;
    public String toString(){
        return  "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username=" + username +
                ", password=" + password +
                ", isActive=" + isActive + ", ";
    }
}
