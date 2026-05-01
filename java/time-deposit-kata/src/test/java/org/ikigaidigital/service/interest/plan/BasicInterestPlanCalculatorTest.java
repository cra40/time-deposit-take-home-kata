package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ikigaidigital.service.interest.plan.PlanType.BASIC;

class BasicInterestPlanCalculatorTest {

    private final BasicInterestPlanCalculator underTest = new BasicInterestPlanCalculator();

    @ParameterizedTest(name = "balance={1}, days={2} -> expected interest={3}")
    @MethodSource("planTypeTestCases")
    void calculateInterest_appliesCorrectInterest(double balance, int days, double expectedBalance) {
        final TimeDeposit timeDeposit = new TimeDeposit(1, BASIC.name(), balance, days);

        BigDecimal actualBalance = underTest.calculateInterest(timeDeposit);

        assertThat(actualBalance).isEqualTo(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP));
    }

    private static Stream<Arguments> planTypeTestCases() {
        return Stream.of(
                Arguments.of(1000.00, 29, 0.00),
                Arguments.of(1000.00, 30, 0.00),
                Arguments.of(1000.00, 45, 0.83)
        );
    }

}