package org.ikigaidigital;

import org.ikigaidigital.adapter.in.dto.TimeDepositWithWithdrawalsDto;
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

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        TimeDepositWithWithdrawalsDto[] actual = restTemplate.getForObject("http://localhost:" + port + "/time-deposits", TimeDepositWithWithdrawalsDto[].class);
        assertThat(actual).hasSize(3);
        assertTrue(Arrays.stream(actual).anyMatch(dto -> dto.id() == 1 && dto.planType().equals("BASIC") && dto.balance().doubleValue() == 1000.00 && dto.days() == 30 && dto.withdrawals().size() == 2));
        assertTrue(Arrays.stream(actual).anyMatch(dto -> dto.id() == 2 && dto.planType().equals("STUDENT") && (dto.balance().doubleValue() == 1000.00 || dto.balance().doubleValue() == 1002.50) && dto.days() == 45 && dto.withdrawals().isEmpty()));
        assertTrue(Arrays.stream(actual).anyMatch(dto -> dto.id() == 3 && dto.planType().equals("PREMIUM") && dto.balance().doubleValue() == 1000.00 && dto.days() == 30 && dto.withdrawals().isEmpty()));

    }

    @Test
    void saveInterests() {
        String expected = """
                [{"id":1,"planType":"BASIC","balance":1000.00,"days":30},{"id":2,"planType":"STUDENT","balance":1002.50,"days":45},{"id":3,"planType":"PREMIUM","balance":1000.00,"days":30}]""";
        TimeDepositWithWithdrawalsDto[] actual = restTemplate.postForObject("http://localhost:" + port + "/time-deposits/interests", null, TimeDepositWithWithdrawalsDto[].class);
        assertThat(actual).hasSize(3);
        assertTrue(Arrays.stream(actual).anyMatch(dto -> dto.id() == 1 && dto.planType().equals("BASIC") && dto.balance().doubleValue() == 1000.00 && dto.days() == 30));
        assertTrue(Arrays.stream(actual).anyMatch(dto -> dto.id() == 2 && dto.planType().equals("STUDENT") && dto.balance().doubleValue() == 1002.50 && dto.days() == 45));
        assertTrue(Arrays.stream(actual).anyMatch(dto -> dto.id() == 3 && dto.planType().equals("PREMIUM") && dto.balance().doubleValue() == 1000.00 && dto.days() == 30));
    }
}