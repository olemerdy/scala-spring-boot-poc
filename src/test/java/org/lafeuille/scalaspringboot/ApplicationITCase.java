package org.lafeuille.scalaspringboot;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = ApplicationITCase.Initializer.class)
public class ApplicationITCase {

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>();

    @Test
    public void contextLoads() {
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            System.out.println(neo4jContainer.getBoltUrl());
            TestPropertyValues.of(
                    "spring.neo4j.uri=" + neo4jContainer.getBoltUrl(),
                    "spring.neo4j.authentication.username=" + "neo4j",
                    "spring.neo4j.authentication.password=" + neo4jContainer.getAdminPassword()
            ).applyTo(context.getEnvironment());
        }
    }
}
