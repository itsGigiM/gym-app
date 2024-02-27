package com.example.taskspring.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
    public User(String firstName, String lastName, String username, String password, boolean isActive) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.isActive = isActive;
    }

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private boolean isActive;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public String toString(){
        return  "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username=" + username +
                ", password=" + password +
                ", isActive=" + isActive + ", ";
    }

}
