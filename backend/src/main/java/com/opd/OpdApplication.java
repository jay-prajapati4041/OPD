package com.opd; // Define base package

import org.springframework.boot.SpringApplication; // Import SpringApplication class
import org.springframework.boot.autoconfigure.SpringBootApplication; // Import SpringBootApplication annotation

@SpringBootApplication // Add annotation to mark this as a Spring Boot application
public class OpdApplication { // Define main class

    public static void main(String[] args) { // Define main method, the entry point
        SpringApplication.run(OpdApplication.class, args); // Run the Spring application
    } // End main method

} // End main class
