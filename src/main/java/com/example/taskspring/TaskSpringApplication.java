package com.example.taskspring;

import com.example.taskspring.service.GymAppFacade;
import com.example.taskspring.utils.InMemoryStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.example.taskspring.service.TraineeService;
import com.example.taskspring.service.TrainerService;
import com.example.taskspring.service.TrainingService;

import java.time.LocalDate;
import java.util.Date;

@SpringBootApplication
public class TaskSpringApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(TaskSpringApplication.class, args);
		GymAppFacade facade = applicationContext.getBean(GymAppFacade.class);
		System.out.println(facade);
		facade.getTraineeService().createTrainee("Malkhaz", "Chaburgia", true, "10"
		, "Kutaisi", LocalDate.of(2005, 9, 21));
		facade.getTraineeService().createTrainee("Malkhaz", "Chaburgia", true, "14"
				, "Kutaisi", LocalDate.of(2005, 9, 21));
		System.out.println(facade);


	}

}
