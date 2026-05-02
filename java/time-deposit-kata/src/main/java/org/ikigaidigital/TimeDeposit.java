package org.ikigaidigital;

import org.ikigaidigital.domain.TimeDepositV2;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** @deprecated Use {@link TimeDepositV2} instead */
public class TimeDeposit {
    private final Integer id;
    private final String planType;
    private Double balance;
    private final Integer days;

    // Assumption: Deprecation is allowed
    // No breaking change here, it is backwards compatible
    // The mvn artifact and the package/class names haven't changed either
    @Deprecated
    public TimeDeposit(int id, String planType, Double balance, int days) {
        this.id = id;
        this.planType = planType;
        this.balance = balance;
        this.days = days;
    }

    public int getId() { return id; }

    public String getPlanType() {
        return planType;
    }

    public Double getBalance() {
        return balance;
    }

    public int getDays() {
        return days;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public BigDecimal getBalanceAsBigDecimal() {
        return balance == null ? null : BigDecimal.valueOf(balance).setScale(2, RoundingMode.HALF_UP);
    }
}
