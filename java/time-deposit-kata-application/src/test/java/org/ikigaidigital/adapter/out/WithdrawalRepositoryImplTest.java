package org.ikigaidigital.adapter.out;

import org.ikigaidigital.adapter.out.entity.TimeDepositEntity;
import org.ikigaidigital.adapter.out.entity.WithdrawalEntity;
import org.ikigaidigital.domain.Withdrawal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ikigaidigital.service.interest.plan.PlanType.BASIC;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawalRepositoryImplTest {

    @Mock
    private WithdrawalJPARepository withdrawalJPARepository;

    @InjectMocks
    private WithdrawalRepositoryImpl underTest;

    @Test
    void findByTimeDepositId_returnsMappedWithdrawals() {
        int timeDepositId = 1;
        TimeDepositEntity timeDepositEntity = new TimeDepositEntity(timeDepositId, BASIC.name(), BigDecimal.valueOf(1000.00), 45);
        Date date1 = new Date();
        Date date2 = new Date();
        List<WithdrawalEntity> entities = List.of(
                new WithdrawalEntity(1, timeDepositEntity, BigDecimal.valueOf(500.00), date1),
                new WithdrawalEntity(2, timeDepositEntity, BigDecimal.valueOf(300.00), date2)
        );
        when(withdrawalJPARepository.findByTimeDepositId(timeDepositId)).thenReturn(entities);

        List<Withdrawal> result = underTest.findByTimeDepositId(timeDepositId);

        verify(withdrawalJPARepository).findByTimeDepositId(timeDepositId);
        assertThat(result).containsExactly(
                new Withdrawal(1, timeDepositId, BigDecimal.valueOf(500.00), date1),
                new Withdrawal(2, timeDepositId, BigDecimal.valueOf(300.00), date2)
        );
    }

    @Test
    void findByTimeDepositId_returnsEmptyListWhenNoWithdrawalsExist() {
        int timeDepositId = 99;
        when(withdrawalJPARepository.findByTimeDepositId(timeDepositId)).thenReturn(Collections.emptyList());

        List<Withdrawal> result = underTest.findByTimeDepositId(timeDepositId);

        verify(withdrawalJPARepository).findByTimeDepositId(timeDepositId);
        assertThat(result).isEmpty();
    }
}
