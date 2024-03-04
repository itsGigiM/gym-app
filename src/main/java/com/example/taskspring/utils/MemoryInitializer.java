package com.example.taskspring.utils;

import com.example.taskspring.model.Trainee;
import com.example.taskspring.model.Trainer;
import com.example.taskspring.model.Training;
import com.example.taskspring.repository.InMemoryStorage;
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
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            log.info("Starting initializing Storage");
            JsonDataContainer root = objectMapper.readValue(configFile.getInputStream(), JsonDataContainer.class);
            fillMaps(storage, root);
            log.info("finished initializing Storage");
        } catch (IOException e) {
            log.error("Error processing JSON file, initializing of the memory failed" + e);
        }
    }

    private void fillMaps(InMemoryStorage storage, JsonDataContainer root) {
        for(Trainee trainee : root.getTrainees()){
            storage.getTraineesData().put(trainee.getTraineeId(), trainee);
        }
        for(Trainer trainer : root.getTrainers()){
            storage.getTrainersData().put(trainer.getTrainerId(), trainer);
        }
        for(Training training : root.getTrainings()){
            storage.getTrainingsData().put(training.getTrainingId(), training);
        }
    }

}
