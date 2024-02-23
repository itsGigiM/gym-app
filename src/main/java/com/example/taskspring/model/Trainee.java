package model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class Trainee extends User{
    private String userId;
    private String address;
    private Date dateOfBirth;
    public Trainee(String firstName, String lastName, String username,
                   String password, boolean isActive, String userId, String address, Date dateOfBirth) {
        super(firstName, lastName, username, password, isActive);
        this.address = address;
        this.userId = userId;
        this.dateOfBirth = dateOfBirth;
    }
}
