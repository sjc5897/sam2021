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
        dataSource.setUrl("jdbc:mysql://bac0a07245e23f:ed3f0950@us-cdbr-east-02.cleardb.com/heroku_62816df5dd20b0a?reconnect=true");

        return dataSource;
    }
}
