package com.app;

import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import org.slf4j.Logger;

@SpringBootApplication
@EnableMongoRepositories//(basePackages = "com.app.repository")  

public class QuizNinjaBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(QuizNinjaBackendApplication.class, args);
    }
    
  
}
