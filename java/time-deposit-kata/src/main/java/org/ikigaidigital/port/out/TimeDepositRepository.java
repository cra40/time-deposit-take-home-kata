package org.ikigaidigital.port.out;

import org.ikigaidigital.domain.TimeDepositV2;

import java.util.List;

public interface TimeDepositRepository {

    List<TimeDepositV2> findAll();

    List<TimeDepositV2> saveAll(List<TimeDepositV2> timeDeposits);
}
