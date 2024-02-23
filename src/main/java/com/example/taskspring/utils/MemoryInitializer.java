package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import java.util.logging.Level;
import java.io.IOException;


@Component
@Slf4j
public class MemoryInitializer implements BeanPostProcessor {
    @Value("${data.file}")
    private Resource configFile;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof InMemoryStorage) {
            initializeStorage((InMemoryStorage) bean);
        }
        return bean;
    }


    private void initializeStorage(InMemoryStorage storage) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            JsonNode node = objectMapper.readTree(configFile.getInputStream());
            log.info("Starting initializing Storage");
            fillTraineeMap(node.get("Trainees"), storage);
            fillTrainerMap(node.get("Trainers"), storage);
            fillTrainingMap(node.get("Trainings"), storage);
            log.info("finished initializing Storage");
        } catch (IOException e) {
            // Handle exception
        }
    }

    private void fillTraineeMap(JsonNode node, InMemoryStorage storage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        for (JsonNode jsonTrainee : node) {
            try {
                Trainee trainee = objectMapper.treeToValue(jsonTrainee, Trainee.class);
                storage.getTraineesData().put(trainee.getTraineeId(), trainee);
            } catch (JsonProcessingException e) {
                log.error("Error processing node: " + jsonTrainee.toString() + "\n" + e);
            }
        }
    }

    private void fillTrainerMap(JsonNode node, InMemoryStorage storage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        for (JsonNode jsonTrainer : node) {
            try {
                Trainer trainer = objectMapper.treeToValue(jsonTrainer, Trainer.class);
                storage.getTrainersData().put(trainer.getTrainerId(), trainer);
            } catch (JsonProcessingException e) {
                log.error("Error processing node: " + jsonTrainer.toString() + "\n" + e);
            }
        }
    }

    private void fillTrainingMap(JsonNode node, InMemoryStorage storage) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.registerModule(new JavaTimeModule());
        for (JsonNode jsonTraining : node) {
            try {
                Training training = objectMapper.treeToValue(jsonTraining, Training.class);
                storage.getTrainingsData().put(training.getTrainingId(), training);
            } catch (JsonProcessingException e) {
                log.error("Error processing node: " + jsonTraining.toString() + "\n" + e);
            }
        }
    }
}
