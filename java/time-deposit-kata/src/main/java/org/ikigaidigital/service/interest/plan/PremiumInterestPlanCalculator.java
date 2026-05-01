package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;

import java.math.BigDecimal;

public class PremiumInterestPlanCalculator implements InterestPlanCalculator {

    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.05);

    @Override
    public BigDecimal calculateInterest(TimeDeposit deposit) {
        if (deposit.getDays() > 45) {
            return calculateMonthlyInterest(deposit.getBalanceAsBigDecimal(), INTEREST_RATE);
        }
        return ZERO_INTEREST;
    }
}
