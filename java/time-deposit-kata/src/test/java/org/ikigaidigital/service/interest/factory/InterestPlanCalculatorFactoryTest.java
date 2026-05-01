package org.ikigaidigital.service.interest.factory;

import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InterestPlanCalculatorFactoryTest {

    private static final String PLAN_TYPE = "planType";

    @Mock
    private InterestPlanCalculator basicCalculator;

    @Mock
    private InterestPlanCalculator studentCalculator;

    @Test
    void getCalculator_returnsCalculatorMatchingPlanType() {
        when(basicCalculator.isInterestPlanApplicable(PLAN_TYPE)).thenReturn(true);
        when(studentCalculator.isInterestPlanApplicable(PLAN_TYPE)).thenReturn(false);

        InterestPlanCalculatorFactory underTest = new InterestPlanCalculatorFactory(List.of(basicCalculator, studentCalculator));
        InterestPlanCalculator result = underTest.getCalculator(PLAN_TYPE);

        assertThat(result).isSameAs(basicCalculator);
    }

    @Test
    void getCalculator_throwsWhenNoPlanMatchesPlanType() {
        when(basicCalculator.isInterestPlanApplicable(PLAN_TYPE)).thenReturn(false);
        InterestPlanCalculatorFactory underTest = new InterestPlanCalculatorFactory(List.of(basicCalculator));

        assertThatThrownBy(() -> underTest.getCalculator(PLAN_TYPE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PLAN_TYPE);
    }

    @Test
    void getCalculator_throwsWhenMultipleCalculatorsMatchPlanType() {
        when(basicCalculator.isInterestPlanApplicable(PLAN_TYPE)).thenReturn(true);
        when(studentCalculator.isInterestPlanApplicable(PLAN_TYPE)).thenReturn(true);
        InterestPlanCalculatorFactory underTest = new InterestPlanCalculatorFactory(List.of(basicCalculator, studentCalculator));

        assertThatThrownBy(() -> underTest.getCalculator(PLAN_TYPE))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining(PLAN_TYPE);
    }
}
