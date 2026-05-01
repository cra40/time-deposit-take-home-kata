package org.ikigaidigital.service;

import org.ikigaidigital.domain.Withdrawal;
import org.ikigaidigital.port.out.WithdrawalRepository;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WithdrawalsServiceImplTest {

    @Mock
    private WithdrawalRepository withdrawalRepository;

    @InjectMocks
    private WithdrawalsServiceImpl underTest;

    @Test
    void getWithdrawalsByTimeDepositId_returnsWithdrawalsFromRepository() {
        int timeDepositId = 1;
        List<Withdrawal> withdrawals = List.of(
                new Withdrawal(1, timeDepositId, BigDecimal.valueOf(500.00), new Date()),
                new Withdrawal(2, timeDepositId, BigDecimal.valueOf(600.00), new Date())
        );
        when(withdrawalRepository.findByTimeDepositId(timeDepositId)).thenReturn(withdrawals);

        List<Withdrawal> actual = underTest.getWithdrawalsByTimeDepositId(timeDepositId);

        verify(withdrawalRepository).findByTimeDepositId(timeDepositId);
        assertThat(actual).isEqualTo(withdrawals);
    }

    @Test
    void getWithdrawalsByTimeDepositId_returnsEmptyListWhenNoWithdrawalsExist() {
        int timeDepositId = 99;
        when(withdrawalRepository.findByTimeDepositId(timeDepositId)).thenReturn(Collections.emptyList());

        List<Withdrawal> result = underTest.getWithdrawalsByTimeDepositId(timeDepositId);

        verify(withdrawalRepository).findByTimeDepositId(timeDepositId);
        assertThat(result).isEmpty();
    }
}
