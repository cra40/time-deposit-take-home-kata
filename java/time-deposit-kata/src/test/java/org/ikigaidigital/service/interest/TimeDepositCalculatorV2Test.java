package org.ikigaidigital.service.interest;

import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;
import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.ikigaidigital.TestUtil.toBigDecimal;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositCalculatorV2Test {

    private static final String PLAN_TYPE = "planType";

    @Mock
    private InterestPlanCalculatorFactory interestPlanCalculatorFactory;

    @Mock
    private InterestPlanCalculator interestPlanCalculator;

    @InjectMocks
    private TimeDepositCalculatorV2 underTest;

    @Test
    void updateBalance_delegatesToFactoryAndAppliesInterestToBalance() {
        TimeDepositV2 deposit = new TimeDepositV2(1, PLAN_TYPE, toBigDecimal(1000.00), 45);
        BigDecimal interest = toBigDecimal(0.83);
        when(interestPlanCalculatorFactory.getCalculator(PLAN_TYPE)).thenReturn(interestPlanCalculator);
        when(interestPlanCalculator.calculateInterest(deposit)).thenReturn(interest);

        List<TimeDepositV2> updatedDeposits = underTest.updateBalance(List.of(deposit));

        verify(interestPlanCalculatorFactory).getCalculator(PLAN_TYPE);
        verify(interestPlanCalculator).calculateInterest(deposit);
        assertThat(updatedDeposits.get(0).balance()).isEqualTo(toBigDecimal(1000.83));
    }

    @Test
    void updateBalance_processesEachDepositIndependently() {
        TimeDepositV2 deposit1 = new TimeDepositV2(1, PLAN_TYPE, toBigDecimal(1000.00), 45);
        TimeDepositV2 deposit2 = new TimeDepositV2(2, PLAN_TYPE, toBigDecimal(2000.00), 45);
        when(interestPlanCalculatorFactory.getCalculator(PLAN_TYPE)).thenReturn(interestPlanCalculator);
        when(interestPlanCalculator.calculateInterest(deposit1)).thenReturn(toBigDecimal(0.83));
        when(interestPlanCalculator.calculateInterest(deposit2)).thenReturn(toBigDecimal(1.67));

        List<TimeDepositV2> updatedDeposits = underTest.updateBalance(List.of(deposit1, deposit2));

        assertThat(updatedDeposits.get(0).balance()).isEqualTo(toBigDecimal(1000.83));
        assertThat(updatedDeposits.get(1).balance()).isEqualTo(toBigDecimal(2001.67));
    }

    @Test
    void updateBalance_propagatesExceptionForUnknownPlanType() {
        TimeDepositV2 deposit = new TimeDepositV2(1, PLAN_TYPE, toBigDecimal(1000.00), 45);
        when(interestPlanCalculatorFactory.getCalculator(PLAN_TYPE))
                .thenThrow(new IllegalArgumentException("Failed to find interest calculation plan for type: " + PLAN_TYPE));

        assertThatThrownBy(() -> underTest.updateBalance(List.of(deposit)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PLAN_TYPE);
    }
}
