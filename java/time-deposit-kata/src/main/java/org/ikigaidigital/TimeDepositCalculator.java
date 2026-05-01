package org.ikigaidigital;

import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;

import java.math.BigDecimal;
import java.util.List;

public class TimeDepositCalculator {

    private final InterestPlanCalculatorFactory interestPlanCalculatorFactory;

    public TimeDepositCalculator(InterestPlanCalculatorFactory interestPlanCalculatorFactory) {
        this.interestPlanCalculatorFactory = interestPlanCalculatorFactory;
    }

    public void updateBalance(List<TimeDeposit> xs) {
        xs.forEach(timeDeposit -> {
            final InterestPlanCalculator calculator = interestPlanCalculatorFactory.getCalculator(timeDeposit.getPlanType());
            final BigDecimal interest = calculator.calculateInterest(timeDeposit);
            final BigDecimal updatedBalance = timeDeposit.getBalanceAsBigDecimal().add(interest);
            timeDeposit.setBalance(updatedBalance);
        });
    }
}
