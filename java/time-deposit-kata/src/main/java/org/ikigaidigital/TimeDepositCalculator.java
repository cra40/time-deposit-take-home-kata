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

    // Assumption: deprecation is allowed and any new functionality developed does not have to use the TimeDeposit and the TimeDepositCalculator classes
    // Any existing usage of these 2 classes should be fine as there are no breaking changes
    public void updateBalance(List<TimeDeposit> xs) {
        xs.forEach(timeDeposit -> {
            final InterestPlanCalculator calculator = interestPlanCalculatorFactory.getCalculator(timeDeposit.getPlanType());
            final BigDecimal interest = calculator.calculateInterest(
                    new TimeDepositV2(timeDeposit.getId(), timeDeposit.getPlanType(), timeDeposit.getBalanceAsBigDecimal(), timeDeposit.getDays())
            );
            final BigDecimal updatedBalance = timeDeposit.getBalanceAsBigDecimal().add(interest);
            timeDeposit.setBalance(updatedBalance.doubleValue());
        });
    }
}
