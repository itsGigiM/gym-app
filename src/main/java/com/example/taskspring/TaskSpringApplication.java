package com.example.taskspring;

import com.example.taskspring.service.GymAppFacade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TaskSpringApplication {

	public static void main(String[] args) {

		ApplicationContext applicationContext = SpringApplication.run(TaskSpringApplication.class, args);
		GymAppFacade facade = applicationContext.getBean(GymAppFacade.class);
		System.out.println(facade);


	}

}
