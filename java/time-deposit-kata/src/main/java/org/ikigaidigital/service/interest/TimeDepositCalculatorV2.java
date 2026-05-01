package org.ikigaidigital.service.interest;

import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;
import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;

import java.math.BigDecimal;
import java.util.List;

public class TimeDepositCalculatorV2 {

    private final InterestPlanCalculatorFactory interestPlanCalculatorFactory;

    public TimeDepositCalculatorV2(InterestPlanCalculatorFactory interestPlanCalculatorFactory) {
        this.interestPlanCalculatorFactory = interestPlanCalculatorFactory;
    }

    public List<TimeDepositV2> updateBalance(List<TimeDepositV2> timeDeposits) {
        return timeDeposits
                .stream()
                .map(this::getUpdatedDeposit)
                .toList();
    }

    private TimeDepositV2 getUpdatedDeposit(TimeDepositV2 timeDeposit) {
        final InterestPlanCalculator calculator = interestPlanCalculatorFactory.getCalculator(timeDeposit.planType());
        final BigDecimal interest = calculator.calculateInterest(timeDeposit);
        final BigDecimal updatedBalance = timeDeposit.balance().add(interest);
        return new TimeDepositV2(timeDeposit.id(), timeDeposit.planType(), updatedBalance, timeDeposit.days());
    }
}
