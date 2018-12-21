package com.example.rxjava2.demoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class DemoapiApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoapiApplication.class, args);
    }

}

