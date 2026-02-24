package com.hrdigital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(
    exclude = {
        DataSourceAutoConfiguration.class
    }
)
@ComponentScan(
    basePackages = {
        "com.hrdigital",
        "com.hrdigital.Application.Controllers"
    }
)
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
