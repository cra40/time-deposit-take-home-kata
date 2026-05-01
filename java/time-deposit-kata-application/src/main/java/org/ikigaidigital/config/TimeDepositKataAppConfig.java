package org.ikigaidigital.config;

import org.ikigaidigital.port.in.TimeDepositService;
import org.ikigaidigital.port.in.WithdrawalService;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.ikigaidigital.port.out.WithdrawalRepository;
import org.ikigaidigital.service.TimeDepositServiceImpl;
import org.ikigaidigital.service.WithdrawalServiceImpl;
import org.ikigaidigital.service.interest.TimeDepositCalculatorV2;
import org.ikigaidigital.service.interest.factory.InterestPlanCalculatorFactory;
import org.ikigaidigital.service.interest.plan.BasicInterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.PremiumInterestPlanCalculator;
import org.ikigaidigital.service.interest.plan.StudentInterestPlanCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan("org.ikigaidigital")
public class TimeDepositKataAppConfig {

    @Bean
    public BasicInterestPlanCalculator basicInterestPlanCalculator() {
        return new BasicInterestPlanCalculator();
    }

    @Bean
    public StudentInterestPlanCalculator studentInterestPlanCalculator() {
        return new StudentInterestPlanCalculator();
    }

    @Bean
    public PremiumInterestPlanCalculator premiumInterestPlanCalculator() {
        return new PremiumInterestPlanCalculator();
    }

    @Bean
    public InterestPlanCalculatorFactory interestPlanCalculatorFactory(BasicInterestPlanCalculator basicInterestPlanCalculator,
                                                                       StudentInterestPlanCalculator studentInterestPlanCalculator,
                                                                       PremiumInterestPlanCalculator premiumInterestPlanCalculator) {
        return new InterestPlanCalculatorFactory(List.of(
                basicInterestPlanCalculator,
                studentInterestPlanCalculator,
                premiumInterestPlanCalculator
        ));
    }

    @Bean
    public TimeDepositCalculatorV2 timeDepositCalculatorV2(InterestPlanCalculatorFactory interestPlanCalculatorFactory) {
        return new TimeDepositCalculatorV2(interestPlanCalculatorFactory);
    }

    @Bean
    public TimeDepositService timeDepositService(TimeDepositRepository timeDepositRepository, TimeDepositCalculatorV2 timeDepositCalculator) {
        return new TimeDepositServiceImpl(timeDepositRepository, timeDepositCalculator);
    }

    @Bean
    public WithdrawalService withdrawalService(WithdrawalRepository withdrawalRepository) {
        return new WithdrawalServiceImpl(withdrawalRepository);
    }
}
