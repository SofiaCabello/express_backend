package org.example.express_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ExpressBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExpressBackendApplication.class, args);
    }

}
