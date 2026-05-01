package org.ikigaidigital.port.out;

import org.ikigaidigital.domain.TimeDepositV2;

import java.util.List;

public interface TimeDepositRepository {

    List<TimeDepositV2> findAll();

    void save(TimeDepositV2 timeDeposit);
}
