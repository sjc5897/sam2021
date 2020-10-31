package com.sam2021;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * This is the main class used to start the application
 * Language: Java 13
 * Framework: Spring
 * Author: Stephen Cook <sjc5897@rit.edu>
 * Created: 10/24/20
 * Edited: 10/24/20
 */
@SpringBootApplication
public class Sam2021Application {

    public static void main(String[] args) {
        SpringApplication.run(Sam2021Application.class, args);
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUsername("sam2020");
        dataSource.setPassword("root");
        dataSource.setUrl("jdbc:mysql://localhost:3307/userdata");

        return dataSource;
    }
}
