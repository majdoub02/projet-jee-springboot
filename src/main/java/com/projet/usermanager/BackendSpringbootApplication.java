package com.projet.usermanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class BackendSpringbootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackendSpringbootApplication.class, args);
    }
}