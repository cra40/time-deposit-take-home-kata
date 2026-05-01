package org.ikigaidigital.adapter.out;

import org.ikigaidigital.adapter.out.entity.WithdrawalEntity;
import org.ikigaidigital.domain.Withdrawal;
import org.ikigaidigital.port.out.WithdrawalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WithdrawalRepositoryImpl implements WithdrawalRepository {

    private final WithdrawalJPARepository withdrawalJPARepository;

    @Autowired
    public WithdrawalRepositoryImpl(WithdrawalJPARepository withdrawalJPARepository) {
        this.withdrawalJPARepository = withdrawalJPARepository;
    }

    @Override
    public List<Withdrawal> findByTimeDepositId(Integer timeDepositId) {
        return withdrawalJPARepository.findByTimeDepositId(timeDepositId)
                .stream()
                .map(this::toWithdrawal)
                .toList();
    }

    private Withdrawal toWithdrawal(WithdrawalEntity entity) {
        return new Withdrawal(
                entity.getId(),
                entity.getTimeDeposit().getId(),
                entity.getAmount(),
                entity.getDate()
        );
    }
}
