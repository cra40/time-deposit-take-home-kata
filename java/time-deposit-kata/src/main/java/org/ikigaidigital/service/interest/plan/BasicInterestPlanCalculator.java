package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BasicInterestPlanCalculator implements InterestPlanCalculator {

    private static final BigDecimal INTEREST_RATE = BigDecimal.valueOf(0.01);

    @Override
    public BigDecimal calculateInterest(TimeDeposit deposit) {
        if (deposit.getDays() > 30) {
            return deposit.getBalanceAsBigDecimal()
                    .multiply(INTEREST_RATE)
                    .divide(MONTHS, 2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
