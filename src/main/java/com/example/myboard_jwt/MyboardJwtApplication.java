package com.example.myboard_jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MyboardJwtApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyboardJwtApplication.class, args);
    }

}
