package org.ikigaidigital.service.interest.plan;

import org.ikigaidigital.TimeDeposit;

import java.math.BigDecimal;

public interface InterestPlanCalculator {

    BigDecimal MONTHS = BigDecimal.valueOf(12);

    BigDecimal calculateInterest(TimeDeposit deposit);

}
