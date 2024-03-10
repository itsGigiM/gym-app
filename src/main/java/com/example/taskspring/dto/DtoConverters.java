package com.example.taskspring.dto;

import com.example.taskspring.dto.traineeDTO.*;
import com.example.taskspring.dto.trainerDTO.BasicTrainerDTO;
import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.service.TrainerService;

import javax.naming.AuthenticationException;
import java.util.HashSet;
import java.util.Set;

public class DtoConverters {

    public static GetTraineeResponseDTO traineeTogetGetTraineeResponseDTO(Trainee trainee) {
        Set<BasicTrainerDTO> basicTrainerDTOs = new HashSet<>();
        for(Trainer t : trainee.getTrainers()){
            basicTrainerDTOs.add(new BasicTrainerDTO(t.getUsername(), t.getFirstName(), t.getLastName(), t.getSpecialization()));
        }
        return new GetTraineeResponseDTO(trainee.getFirstName(), trainee.getLastName(), trainee.isActive(),
                trainee.getAddress(), trainee.getDateOfBirth(), basicTrainerDTOs);
    }

    public static void putTraineeRequestDTOUpdates(Trainee existingTrainee, PutTraineeRequestDTO putTraineeRequestDTO){
        existingTrainee.setUsername(putTraineeRequestDTO.getUsername());
        existingTrainee.setFirstName(putTraineeRequestDTO.getFirstName());
        existingTrainee.setLastName(putTraineeRequestDTO.getLastName());
        existingTrainee.setDateOfBirth(putTraineeRequestDTO.getDateOfBirth());
        existingTrainee.setAddress(putTraineeRequestDTO.getAddress());
        existingTrainee.setActive(putTraineeRequestDTO.isActive());
    }

    public static Set<TrainerListResponseTrainerDTO> getTrainerListResponseTrainerDTOS(UpdateTraineeTrainerListRequestDTO updateTraineeTrainingListRequestDTO, Trainee trainee, TrainerService trainerService) throws AuthenticationException {
        Set<Trainer> trainerList = new HashSet<>();
        Set<TrainerListResponseTrainerDTO> trainerDTOList = new HashSet<>();
        for (String trainerUsername : updateTraineeTrainingListRequestDTO.getTrainersList()) {
            Trainer trainer = trainerService.selectTrainer(trainerUsername, "admin1", "admin1");
            trainerList.add(trainer);
            trainerDTOList.add(new TrainerListResponseTrainerDTO(trainer.getUsername(), trainer.getFirstName(), trainer.getLastName(),
                    trainer.getSpecialization()));
        }
        trainee.setTrainers(trainerList);
        return trainerDTOList;
    }

    public static Set<TrainingListResponseTrainingDTO> getTrainingListResponseTrainingDTOS(Set<Training> trainings) {
        Set<TrainingListResponseTrainingDTO> trainingDTOList = new HashSet<>();
        for(Training training : trainings){
            trainingDTOList.add(new TrainingListResponseTrainingDTO(training.getTrainingName(), training.getTrainingDate(),
                    training.getTrainingType(), training.getDuration(), training.getTrainingName()));
        }
        return trainingDTOList;
    }

}
