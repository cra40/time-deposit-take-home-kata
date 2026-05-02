package org.ikigaidigital.service;

import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.port.in.TimeDepositService;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.ikigaidigital.service.interest.TimeDepositCalculatorV2;

import java.util.List;

public class TimeDepositServiceImpl implements TimeDepositService {

    private final TimeDepositRepository timeDepositRepository;
    private final TimeDepositCalculatorV2 timeDepositCalculator;

    public TimeDepositServiceImpl(TimeDepositRepository timeDepositRepository,
                                  TimeDepositCalculatorV2 timeDepositCalculator) {
        this.timeDepositRepository = timeDepositRepository;
        this.timeDepositCalculator = timeDepositCalculator;
    }

    @Override
    public List<TimeDepositV2> getTimeDeposits() {
        return timeDepositRepository.findAll();
    }

    @Override
    public List<TimeDepositV2> updateTimeDepositBalances() {
        List<TimeDepositV2> timeDeposits = timeDepositRepository.findAll();
        return timeDepositRepository.saveAll(timeDepositCalculator.updateBalance(timeDeposits));
    }
}
