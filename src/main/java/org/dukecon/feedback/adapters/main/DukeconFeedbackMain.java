package org.dukecon.feedback.adapters.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "org.dukecon.feedback")
@EnableAutoConfiguration
@EnableJpaRepositories("org.dukecon.feedback.adapters.jpa")
@EntityScan(basePackages = "org.dukecon.feedback.adapters.jpa.model")
public class DukeconFeedbackMain {

    public static void main(String[] args) {
        SpringApplication.run(DukeconFeedbackMain.class, args);
    }
}

