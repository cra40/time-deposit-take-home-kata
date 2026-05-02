package org.ikigaidigital.port.in;

import org.ikigaidigital.domain.TimeDepositV2;

import java.util.List;

public interface TimeDepositService {

    List<TimeDepositV2> getTimeDeposits();

    List<TimeDepositV2> updateTimeDepositBalances();

}
