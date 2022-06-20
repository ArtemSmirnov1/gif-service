package com.exchanging_rate_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:service.properties")
@SpringBootApplication
@EnableFeignClients("com.exchanging_rate_service.services")
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
