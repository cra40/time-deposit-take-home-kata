package org.ikigaidigital;

import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositCalculatorTest {

    private static final String PLAN_TYPE = "planType";

    @Mock
    private InterestPlanCalculatorFactory interestPlanCalculatorFactory;

    @Mock
    private InterestPlanCalculator interestPlanCalculator;

    @InjectMocks
    private TimeDepositCalculator underTest;

    @Test
    void updateBalance_delegatesToFactoryAndAppliesInterestToBalance() {
        TimeDeposit deposit = new TimeDeposit(1, PLAN_TYPE, 1000.00, 45);
        BigDecimal interest = toBigDecimal(0.83);
        when(interestPlanCalculatorFactory.getCalculator(PLAN_TYPE)).thenReturn(interestPlanCalculator);
        when(interestPlanCalculator.calculateInterest(deposit)).thenReturn(interest);

        underTest.updateBalance(List.of(deposit));

        verify(interestPlanCalculatorFactory).getCalculator(PLAN_TYPE);
        verify(interestPlanCalculator).calculateInterest(deposit);
        assertThat(deposit.getBalance()).isEqualTo(1000.83);
    }

    @Test
    void updateBalance_processesEachDepositIndependently() {
        TimeDeposit deposit1 = new TimeDeposit(1, PLAN_TYPE, 1000.00, 45);
        TimeDeposit deposit2 = new TimeDeposit(2, PLAN_TYPE, 2000.00, 45);
        when(interestPlanCalculatorFactory.getCalculator(PLAN_TYPE)).thenReturn(interestPlanCalculator);
        when(interestPlanCalculator.calculateInterest(deposit1)).thenReturn(toBigDecimal(0.83));
        when(interestPlanCalculator.calculateInterest(deposit2)).thenReturn(toBigDecimal(1.67));

        underTest.updateBalance(List.of(deposit1, deposit2));

        assertThat(deposit1.getBalance()).isEqualTo(1000.83);
        assertThat(deposit2.getBalance()).isEqualTo(2001.67);
    }

    @Test
    void updateBalance_propagatesExceptionForUnknownPlanType() {
        TimeDeposit deposit = new TimeDeposit(1, PLAN_TYPE, 1000.00, 45);
        when(interestPlanCalculatorFactory.getCalculator(PLAN_TYPE))
                .thenThrow(new IllegalArgumentException("Failed to find interest calculation plan for type: " + PLAN_TYPE));

        assertThatThrownBy(() -> underTest.updateBalance(List.of(deposit)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PLAN_TYPE);
    }

    private static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
