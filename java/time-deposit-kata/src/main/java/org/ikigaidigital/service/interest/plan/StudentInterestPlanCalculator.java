package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;

import java.math.BigDecimal;

public class StudentInterestPlanCalculator implements InterestPlanCalculator {

    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.03);

    @Override
    public BigDecimal calculateInterest(TimeDeposit deposit) {
        if (deposit.getDays() > 30 && deposit.getDays() < 366) {
            return calculateMonthlyInterest(deposit.getBalanceAsBigDecimal(), INTEREST_RATE);
        }
        return BigDecimal.ZERO;
    }
}
