package org.ikigaidigital;

import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.service.interest.TimeDepositCalculatorV2;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;
import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;

import java.math.BigDecimal;
import java.util.List;

/** @deprecated Use {@link TimeDepositCalculatorV2} instead */
public class TimeDepositCalculator {

    private final InterestPlanCalculatorFactory interestPlanCalculatorFactory;

    public TimeDepositCalculator(InterestPlanCalculatorFactory interestPlanCalculatorFactory) {
        this.interestPlanCalculatorFactory = interestPlanCalculatorFactory;
    }

    public void updateBalance(List<TimeDeposit> xs) {
        xs.forEach(timeDeposit -> {
            final InterestPlanCalculator calculator = interestPlanCalculatorFactory.getCalculator(timeDeposit.getPlanType());
            final BigDecimal interest = calculator.calculateInterest(
                    new TimeDepositV2(timeDeposit.getId(), timeDeposit.getPlanType(), timeDeposit.getBalanceAsBigDecimal(), timeDeposit.getDays())
            );
            final BigDecimal updatedBalance = timeDeposit.getBalanceAsBigDecimal().add(interest);
            timeDeposit.setBalance(updatedBalance);
        });
    }
}
