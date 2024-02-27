package com.example.taskspring;

import com.example.taskspring.service.GymAppFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.time.LocalDate;

@SpringBootApplication
public class TaskSpringApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(TaskSpringApplication.class, args);
		GymAppFacade facade = applicationContext.getBean(GymAppFacade.class);
		System.out.println(facade);
		facade.getTraineeService().createTrainee("Malkhaz", "Chaburgia", true, 10L
		, "Kutaisi", LocalDate.of(2005, 9, 21));
		facade.getTraineeService().createTrainee("Malkhaz", "Chaburgia", true, 14L
				, "Kutaisi", LocalDate.of(2005, 9, 21));
		System.out.println(facade);
		try {
			facade.getTraineeService().deleteTrainee(53L, "Malkhaz.Chaburgia", "l1GkUSNnlJ");
		}catch (Exception e){
			System.out.println(e.toString());
		}


	}

}
