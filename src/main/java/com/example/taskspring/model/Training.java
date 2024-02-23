package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Training {
    private String trainingId;

    private String traineeId;
    private String trainerId;
    private String trainingName;
    private TrainingType trainingType;
    private Date trainingDate;
    private Duration duration;

}
