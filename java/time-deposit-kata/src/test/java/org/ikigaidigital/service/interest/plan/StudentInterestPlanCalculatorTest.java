package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.domain.TimeDepositV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ikigaidigital.TestUtil.toBigDecimal;
import static org.ikigaidigital.service.interest.plan.PlanType.STUDENT;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentInterestPlanCalculatorTest {

    private final StudentInterestPlanCalculator underTest = new StudentInterestPlanCalculator();

    @ParameterizedTest(name = "balance={1}, days={2} -> expected interest={3}")
    @MethodSource("planTypeTestCases")
    void calculateInterest_appliesCorrectInterest(double balance, int days, double expectedBalance) {
        final TimeDepositV2 timeDeposit = new TimeDepositV2(1, STUDENT.name(), toBigDecimal(balance), days);

        BigDecimal actualBalance = underTest.calculateInterest(timeDeposit);

        assertThat(actualBalance).isEqualTo(BigDecimal.valueOf(expectedBalance).setScale(2, RoundingMode.HALF_UP));
    }

    @Test
    void calculateInterest_throwsExceptionWhenBalanceIsNull() {
        final TimeDepositV2 timeDeposit = new TimeDepositV2(1, STUDENT.name(), null, 50);

        assertThatThrownBy(() -> underTest.calculateInterest(timeDeposit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Balance and interest rate must not be null");
    }

    @Test
    void isInterestPlanApplicable_returnsTrueForStudentPlanType() {
        assertTrue(underTest.isInterestPlanApplicable(STUDENT.name()));
    }

    @ParameterizedTest
    @EnumSource(value = PlanType.class, names = {"STUDENT"}, mode = EnumSource.Mode.EXCLUDE)
    void isInterestPlanApplicable_returnsFalseForNonStudentPlanType(PlanType type) {
        assertFalse(underTest.isInterestPlanApplicable(type.name()));
    }

    private static Stream<Arguments> planTypeTestCases() {
        return Stream.of(
                Arguments.of(1000.00, 1, 0.00),
                Arguments.of(1000.00, 30, 0.00),
                Arguments.of(1000.00, 366, 0.00),
                Arguments.of(1000.00, 400, 0.00),
                Arguments.of(1000.00, 31, 2.50),
                Arguments.of(1000.00, 365, 2.50)
        );
    }
}