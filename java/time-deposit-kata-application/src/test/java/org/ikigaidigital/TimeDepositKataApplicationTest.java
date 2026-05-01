package org.ikigaidigital;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource(
        locations = "classpath:application-test.properties")
class TimeDepositKataApplicationTest {

    private static final PostgreSQLContainer<?> CONTAINER = new PostgreSQLContainer<>("postgres:11")
            .withDatabaseName("testdatabase")
            .withUsername("testuser")
            .withPassword("testpassword");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", CONTAINER::getJdbcUrl);
    }

    @BeforeAll
    static void start() {
        CONTAINER.start();
    }

    @AfterAll
    static void stop() {
        CONTAINER.stop();
    }

    @Test
    void applicationContextLoads() {
        assertTrue(true);
    }
}