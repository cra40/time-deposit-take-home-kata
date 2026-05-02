package org.ikigaidigital;

import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.port.in.TimeDepositService;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.ikigaidigital.service.TimeDepositServiceImpl;
import org.ikigaidigital.service.interest.TimeDepositCalculatorV2;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;
import org.ikigaidigital.service.interest.plan.BasicInterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.PremiumInterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.StudentInterestPlanCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ikigaidigital.TestUtil.toBigDecimal;
import static org.mockito.Mockito.*;

public class TimeDepositServiceTest {

    private static final String BASIC_PLAN_TYPE = "basic";
    private static final String STUDENT_PLAN_TYPE = "student";
    private static final String PREMIUM_PLAN_TYPE = "premium";

    private final List<InterestPlanCalculator> calculators = List.of(
            new BasicInterestPlanCalculator(),
            new StudentInterestPlanCalculator(),
            new PremiumInterestPlanCalculator()
    );

    private TimeDepositRepository repository;
    private TimeDepositService underTest;

    @BeforeEach
    void setUp() {
        repository = mock(TimeDepositRepository.class);
        underTest = new TimeDepositServiceImpl(
                repository,
                new TimeDepositCalculatorV2(new InterestPlanCalculatorFactory(calculators))
        );
    }

    @ParameterizedTest(name = "{0}: balance={1}, days={2} -> expected={3}")
    @MethodSource("planTypeTestCases")
    void updateBalance_appliesCorrectInterestByPlanType(String planType, double balance, int days, double expectedBalance) {
        final List<TimeDepositV2> deposits = List.of(new TimeDepositV2(1, planType, toBigDecimal(balance), days));
        when(repository.findAll()).thenReturn(deposits);
        when(repository.saveAll(any())).thenAnswer(invocation -> invocation.getArgument(0));

        final List<TimeDepositV2> updatedDeposits = underTest.updateTimeDepositBalances();

        assertThat(updatedDeposits.get(0).balance()).isEqualTo(toBigDecimal(expectedBalance));
    }

    private static Stream<Arguments> planTypeTestCases() {
        return Stream.of(
                // no interest added
                Arguments.of(BASIC_PLAN_TYPE, 1000.00, 29, 1000.00),
                Arguments.of(BASIC_PLAN_TYPE, 1000.00, 30, 1000.00),

                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 1, 1000.00),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 30, 1000.00),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 366, 1000.00),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 400, 1000.00),

                Arguments.of(PREMIUM_PLAN_TYPE, 1000.00, 30, 1000.00),
                Arguments.of(PREMIUM_PLAN_TYPE, 1000.00, 45, 1000.00),

                // interest added
                Arguments.of(BASIC_PLAN_TYPE, 1000.00, 45, 1000.83),
                Arguments.of(STUDENT_PLAN_TYPE, 1000.00, 31, 1002.50),
                Arguments.of(PREMIUM_PLAN_TYPE, 1000.00, 60, 1004.17)
        );
    }
}
