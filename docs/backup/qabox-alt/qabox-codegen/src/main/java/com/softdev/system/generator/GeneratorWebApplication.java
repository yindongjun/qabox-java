package com.softdev.system.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer; 

@SpringBootApplication
public class GeneratorWebApplication extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(GeneratorWebApplication.class);
  } 
  
  public static void main(String[] args) {
    SpringApplication.run(GeneratorWebApplication.class, args);
  }

}
