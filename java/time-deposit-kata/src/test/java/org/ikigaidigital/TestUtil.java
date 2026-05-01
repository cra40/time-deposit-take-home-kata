package org.ikigaidigital;

import org.ikigaidigital.domain.TimeDepositV2;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TestUtil {

    public static TimeDepositV2 toTimeDepositV2(TimeDeposit deposit) {
        return new TimeDepositV2(deposit.getId(), deposit.getPlanType(), toBigDecimal(deposit.getBalance()), deposit.getDays());
    }

    public static BigDecimal toBigDecimal(double value) {
        return BigDecimal.valueOf(value).setScale(2, RoundingMode.HALF_UP);
    }
}
