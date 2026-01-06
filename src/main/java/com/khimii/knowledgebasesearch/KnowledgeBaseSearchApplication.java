package com.khimii.knowledgebasesearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.khimii.knowledgebasesearch.repository")
public class KnowledgeBaseSearchApplication {

    static void main(String[] args) {
        SpringApplication.run(KnowledgeBaseSearchApplication.class, args);
    }

}
