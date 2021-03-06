package org.lafeuille.scalaspringboot;

import org.junit.jupiter.api.Test;
import org.lafeuille.scalaspringboot.domain.event.Event;
import org.lafeuille.scalaspringboot.domain.event.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.Neo4jContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
public class ApplicationITCase {

    @Container
    private static final Neo4jContainer<?> neo4jContainer = new Neo4jContainer<>("neo4j");

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", neo4jContainer::getBoltUrl);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", neo4jContainer::getAdminPassword);
    }

    @Autowired
    private EventRepository repository;

    @Test
    public void contextLoads() {
        var saved = repository.save(new Event());
        assertThat(saved).extracting("id").isNotNull();

        var all = repository.findAll();
        assertThat(all).hasSize(1);
    }
}
