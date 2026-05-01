package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.domain.TimeDepositV2;

import java.math.BigDecimal;

import static org.ikigaidigital.service.interest.plan.PlanType.STUDENT;

public class StudentInterestPlanCalculator implements InterestPlanCalculator {

    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.03);

    @Override
    public BigDecimal calculateInterest(TimeDepositV2 deposit) {
        if (deposit.days() > 30 && deposit.days() < 366) {
            return calculateMonthlyInterest(deposit.balance(), INTEREST_RATE);
        }
        return ZERO_INTEREST;
    }

    @Override
    public String getPlanType() {
        return STUDENT.name();
    }
}
