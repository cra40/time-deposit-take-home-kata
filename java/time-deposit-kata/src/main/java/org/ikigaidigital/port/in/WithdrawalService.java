package org.ikigaidigital.port.in;

import org.ikigaidigital.domain.Withdrawal;

import java.util.List;

public interface WithdrawalService {

    List<Withdrawal> getWithdrawalsByTimeDepositId(Integer timeDepositId);
}
