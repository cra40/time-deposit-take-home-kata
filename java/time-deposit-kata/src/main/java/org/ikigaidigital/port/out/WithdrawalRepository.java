package org.ikigaidigital.port.out;

import org.ikigaidigital.domain.Withdrawal;

import java.util.List;

public interface WithdrawalRepository {

    List<Withdrawal> findByTimeDepositId(Integer timeDepositId);
}
