package org.ikigaidigital;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(
        locations = "classpath:application-test.properties")
class TimeDepositKataApplicationIntegrationTest {

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

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getTimeDeposits() {
        String expected = """
                {"id":1,"planType":"BASIC","balance":1000.00,"days":30,"withdrawals":[{"id":1,"timeDepositId":1,"amount":100.00,"date":"2020-01-01T00:00:00.000+00:00"},{"id":2,"timeDepositId":1,"amount":100.00,"date":"2020-01-02T00:00:00.000+00:00"}]},{"id":2,"planType":"STUDENT","balance":1000.00,"days":45,"withdrawals":[]},{"id":3,"planType":"PREMIUM","balance":1000.00,"days":30,"withdrawals":[]}""";
        String actual = restTemplate.getForObject("http://localhost:" + port + "/time-deposits", String.class);
        assertThat(actual).contains(expected);
    }
}