package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;

import java.math.BigDecimal;

import static org.ikigaidigital.service.interest.plan.PlanType.BASIC;

public class BasicInterestPlanCalculator implements InterestPlanCalculator {

    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.01);

    @Override
    public BigDecimal calculateInterest(TimeDeposit deposit) {
        if (deposit.getDays() > 30) {
            return calculateMonthlyInterest(deposit.getBalanceAsBigDecimal(), INTEREST_RATE);
        }
        return ZERO_INTEREST;
    }

    @Override
    public String getPlanType() {
        return BASIC.name();
    }
}
