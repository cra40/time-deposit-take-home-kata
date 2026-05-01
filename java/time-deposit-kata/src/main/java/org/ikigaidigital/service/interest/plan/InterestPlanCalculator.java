package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface InterestPlanCalculator {

    BigDecimal ZERO_INTEREST = BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    BigDecimal MONTHS = BigDecimal.valueOf(12);

    BigDecimal calculateInterest(TimeDeposit deposit);

    default BigDecimal calculateMonthlyInterest(BigDecimal balance, BigDecimal interestRate) {
        return balance.multiply(interestRate).divide(MONTHS, 2, RoundingMode.HALF_UP);
    }

    default String getPlanType() {
        return "";
    }

    default boolean isInterestPlanApplicable(String planType) {
        return getPlanType().equalsIgnoreCase(planType);
    }

}
