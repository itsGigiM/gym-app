package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Trainer extends User{
    private String specialization;
    private String userId;
    public Trainer(String firstName, String lastName, String username, String password, boolean isActive, String specialization, String userId) {
        super(firstName, lastName, username, password, isActive);
        this.specialization = specialization;
        this.userId = userId;
    }
}
