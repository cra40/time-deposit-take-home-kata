package org.ikigaidigital.service;

import org.ikigaidigital.domain.Withdrawal;
import org.ikigaidigital.port.in.WithdrawalService;
import org.ikigaidigital.port.out.WithdrawalRepository;

import java.util.List;

public class WithdrawalsServiceImpl implements WithdrawalService {

    private final WithdrawalRepository withdrawalRepository;

    public WithdrawalsServiceImpl(WithdrawalRepository withdrawalRepository) {
        this.withdrawalRepository = withdrawalRepository;
    }

    @Override
    public List<Withdrawal> getWithdrawalsByTimeDepositId(Integer timeDepositId) {
        return withdrawalRepository.findByTimeDepositId(timeDepositId);
    }

}
