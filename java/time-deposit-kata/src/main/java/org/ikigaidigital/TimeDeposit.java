package org.ikigaidigital;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class TimeDeposit {
    private final int id;
    private final String planType;
    private BigDecimal balance;
    private final int days;

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
