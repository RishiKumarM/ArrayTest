package com.soccertennisgame.soccertennis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = "com.soccertennisgame.soccertennis")
public class SoccertennisApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoccertennisApplication.class, args);
	}

}
