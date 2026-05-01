package org.ikigaidigital;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class TimeDepositCalculatorTest {

    private static final String BASIC_PLAN_TYPE = "basic";
    private static final String STUDENT_PLAN_TYPE = "student";
    private static final String PREMIUM_PLAN_TYPE = "premium";

    private final TimeDepositCalculator calculator = new TimeDepositCalculator();

    @ParameterizedTest(name = "{0}: balance={1}, days={2} -> expected={3}")
    @MethodSource("planTypeTestCases")
    void updateBalance_appliesCorrectInterestByPlanType(String planType, double balance, int days, double expectedBalance) {
        List<TimeDeposit> deposits = List.of(new TimeDeposit(1, planType, balance, days));

        calculator.updateBalance(deposits);

        assertThat(deposits.get(0).getBalance()).isEqualTo(expectedBalance);
    }

    private static Stream<Arguments> planTypeTestCases() {
        return Stream.of(
                // no interest added
                Arguments.of(BASIC_PLAN_TYPE, 1000.00, 29, 1000.00),
                Arguments.of(BASIC_PLAN_TYPE, 1000.00, 30, 1000.00),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 1, 1000.00),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 30, 1000.00),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 366, 1000.00),
                Arguments.of(PREMIUM_PLAN_TYPE, 1000.00, 30, 1000.00),
                Arguments.of(PREMIUM_PLAN_TYPE, 1000.00, 45, 1000.00),

                // interest added
                Arguments.of("basic", 1000.00, 45, 1000.83),
                Arguments.of("student", 1000.00, 31, 1002.50),
                Arguments.of("premium", 1000.00, 60, 1004.17)
        );
    }
}
