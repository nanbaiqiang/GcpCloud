package com.lxg.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

  
@EnableAutoConfiguration
@org.springframework.boot.autoconfigure.SpringBootApplication
public class Application {

    public static void main(String[] args){
        new SpringApplication(Application.class).run(args);
    }
}
//