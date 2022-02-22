package com.example.mymagazine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class MymagazineApplication {

    public static void main(String[] args) {
        SpringApplication.run(MymagazineApplication.class, args);
    }

}
