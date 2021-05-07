package com.ligz.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class DockerAwesomeApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerAwesomeApplication.class, args);
    }

}
