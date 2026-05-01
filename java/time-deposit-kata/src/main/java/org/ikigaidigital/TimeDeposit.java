package org.ikigaidigital;

import org.ikigaidigital.domain.TimeDepositV2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** @deprecated Use {@link TimeDepositV2} instead */
public class TimeDeposit {
    private final Integer id;
    private final String planType;
    private BigDecimal balance;
    private final Integer days;

    @Deprecated
    public TimeDeposit(int id, String planType, Double balance, int days) {
        this.id = id;
        this.planType = planType;
        this.balance = toBigDecimal(balance);
        this.days = days;
    }

    public int getId() { return id; }

    public String getPlanType() {
        return planType;
    }

    public Double getBalance() {
        return balance.doubleValue();
    }

    public int getDays() {
        return days;
    }

    public void setBalance(Double balance) {
        this.balance = toBigDecimal(balance);
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalanceAsBigDecimal() {
        return balance;
    }

    private BigDecimal toBigDecimal(Double balance) {
        return BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_UP);
    }
}
