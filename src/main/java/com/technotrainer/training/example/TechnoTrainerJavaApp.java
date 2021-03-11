package com.technotrainer.training.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechnoTrainerJavaApp {
    private static final Logger logger = LoggerFactory.getLogger(TechnoTrainerJavaApp.class);

    public static void main(String[] args) {
        logger.info("Starting Techno Trainer JavaBasedApp ");
        SpringApplication.run(TechnoTrainerJavaApp.class, args);
    }
}
