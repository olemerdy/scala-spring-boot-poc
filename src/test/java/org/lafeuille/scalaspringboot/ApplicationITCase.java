package org.lafeuille.scalaspringboot;

import org.junit.jupiter.api.Test;
import org.lafeuille.scalaspringboot.domain.event.Event;
import org.lafeuille.scalaspringboot.domain.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
@ContextConfiguration(initializers = ApplicationITCase.Initializer.class)
public class ApplicationITCase {

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j");

    @Autowired
    private EventRepository repository;

    @Test
    public void contextLoads() {
        Mono<Event> saved = repository.save(new Event());
        StepVerifier.create(saved)
                .expectNextMatches(e -> Objects.nonNull(e.getId()))
                .verifyComplete();
        Flux<Event> all = repository.findAll();
        StepVerifier.create(all)
                .expectNextCount(1)
                .verifyComplete();
    }

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext context) {
            TestPropertyValues.of(
                    "spring.neo4j.uri=" + neo4jContainer.getBoltUrl(),
                    "spring.neo4j.authentication.username=" + "neo4j",
                    "spring.neo4j.authentication.password=" + neo4jContainer.getAdminPassword()
            ).applyTo(context.getEnvironment());
        }
    }
}
