package org.ikigaidigital.service.interest.factory;

import org.ikigaidigital.service.interest.plan.InterestPlanCalculator;

import java.util.List;

public class InterestPlanCalculatorFactory {

    private final List<InterestPlanCalculator> calculators;

    public InterestPlanCalculatorFactory(List<InterestPlanCalculator> calculators) {
        this.calculators = calculators;
    }

    public InterestPlanCalculator getCalculator(String planType) {
        List<InterestPlanCalculator> calculatorsForType = calculators.stream()
                .filter(c -> c.isInterestPlanApplicable(planType)).toList();
        if (calculatorsForType.size() != 1) {
            throw new IllegalArgumentException("Failed to find interest calculation plan for type: " + planType);
        }
        return calculatorsForType.get(0);
    }

}
