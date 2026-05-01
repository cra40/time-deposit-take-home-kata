package org.ikigaidigital;

import org.ikigaidigital.service.interest.plan.BasicInterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.PremiumInterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.StudentInterestPlanCalculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class TimeDepositCalculator {
    public void updateBalance(List<TimeDeposit> xs) {
        for (int i = 0; i < xs.size(); i++) {
            double interest = 0;

            if (xs.get(i).getPlanType().equals("basic")) {
                interest = new BasicInterestPlanCalculator().calculateInterest(xs.get(i)).doubleValue();
            } else if (xs.get(i).getPlanType().equals("student")) {
                interest += new StudentInterestPlanCalculator().calculateInterest(xs.get(i)).doubleValue();
            } else if (xs.get(i).getPlanType().equals("premium")) {
                interest += new PremiumInterestPlanCalculator().calculateInterest(xs.get(i)).doubleValue();
            }

            double a2d = xs.get(i).getBalance() + (new BigDecimal(interest).setScale(2, RoundingMode.HALF_UP)).doubleValue();
            xs.get(i).setBalance(a2d);
        }
    }
}
