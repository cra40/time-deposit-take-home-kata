package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.domain.TimeDepositV2;

import java.math.BigDecimal;

import static org.ikigaidigital.service.interest.plan.PlanType.PREMIUM;

public class PremiumInterestPlanCalculator implements InterestPlanCalculator {

    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.05);

    @Override
    public BigDecimal calculateInterest(TimeDepositV2 deposit) {
        if (deposit.days() > 45) {
            return calculateMonthlyInterest(deposit.balance(), INTEREST_RATE);
        }
        return ZERO_INTEREST;
    }

    @Override
    public String getPlanType() {
        return PREMIUM.name();
    }
}
