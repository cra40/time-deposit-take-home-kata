package org.ikigaidigital.service;

import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.ikigaidigital.service.interest.TimeDepositCalculatorV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ikigaidigital.service.interest.plan.PlanType.BASIC;
import static org.ikigaidigital.service.interest.plan.PlanType.STUDENT;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositServiceImplTest {

    @Mock
    private TimeDepositRepository timeDepositRepository;

    @Mock
    private TimeDepositCalculatorV2 timeDepositCalculator;

    @InjectMocks
    private TimeDepositServiceImpl underTest;

    @Test
    void getTimeDeposits_returnsAllDepositsFromRepository() {
        List<TimeDepositV2> deposits = List.of(
            new TimeDepositV2(1, BASIC.name(), BigDecimal.valueOf(1000.00), 45)
        );
        when(timeDepositRepository.findAll()).thenReturn(deposits);

        List<TimeDepositV2> result = underTest.getTimeDeposits();

        verify(timeDepositRepository).findAll();
        assertThat(result).isEqualTo(deposits);
    }

    @Test
    void updateTimeDepositBalances_savesEachUpdatedDepositIndividually() {
        TimeDepositV2 original1 = new TimeDepositV2(1, BASIC.name(), BigDecimal.valueOf(1000.00), 45);
        TimeDepositV2 original2 = new TimeDepositV2(2, STUDENT.name(), BigDecimal.valueOf(2000.00), 90);
        TimeDepositV2 updated1 = new TimeDepositV2(1, BASIC.name(), BigDecimal.valueOf(1000.83), 45);
        TimeDepositV2 updated2 = new TimeDepositV2(2, STUDENT.name(), BigDecimal.valueOf(2005.00), 90);
        List<TimeDepositV2> originals = List.of(original1, original2);
        when(timeDepositRepository.findAll()).thenReturn(originals);
        when(timeDepositCalculator.updateBalance(originals)).thenReturn(List.of(updated1, updated2));

        underTest.updateTimeDepositBalances();

        verify(timeDepositRepository).save(List.of(updated1, updated2));
    }
}
