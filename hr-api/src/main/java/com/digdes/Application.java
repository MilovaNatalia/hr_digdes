package com.digdes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import({HibernateConfig.class})
@SpringBootApplication
public class Application {

    //todo: application properties
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
