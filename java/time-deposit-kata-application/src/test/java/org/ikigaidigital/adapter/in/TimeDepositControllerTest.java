package org.ikigaidigital.adapter.in;

import org.ikigaidigital.adapter.in.dto.TimeDepositDto;
import org.ikigaidigital.adapter.in.dto.TimeDepositWithWithdrawalsDto;
import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.domain.Withdrawal;
import org.ikigaidigital.port.in.TimeDepositService;
import org.ikigaidigital.port.in.WithdrawalService;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositControllerTest {

    @Mock
    private TimeDepositService timeDepositService;

    @Mock
    private WithdrawalService withdrawalService;

    @InjectMocks
    private TimeDepositController underTest;

    @Test
    void getTimeDeposits_returnsMappedTimeDeposits() {
        TimeDepositV2 deposit = new TimeDepositV2(1, BASIC.name(), BigDecimal.valueOf(1000.00), 45);
        Withdrawal withdrawal1 = new Withdrawal(1, 1, BigDecimal.valueOf(500.00), new Date());
        Withdrawal withdrawal2 = new Withdrawal(2, 1, BigDecimal.valueOf(100.00), new Date());
        when(timeDepositService.getTimeDeposits()).thenReturn(List.of(deposit));
        when(withdrawalService.getWithdrawalsByTimeDepositId(1)).thenReturn(List.of(withdrawal1, withdrawal2));

        List<TimeDepositWithWithdrawalsDto> actual = underTest.getTimeDeposits();

        assertThat(actual).hasSize(1);

        assertThat(actual.get(0).id()).isEqualTo(1);
        assertThat(actual.get(0).planType()).isEqualTo(BASIC.name());
        assertThat(actual.get(0).balance()).isEqualTo(BigDecimal.valueOf(1000.00));
        assertThat(actual.get(0).days()).isEqualTo(45);

        assertThat(actual.get(0).withdrawals()).hasSize(2);

        assertThat(actual.get(0).withdrawals().get(0).id()).isEqualTo(withdrawal1.id());
        assertThat(actual.get(0).withdrawals().get(0).timeDepositId()).isEqualTo(withdrawal1.timeDepositId());
        assertThat(actual.get(0).withdrawals().get(0).amount()).isEqualTo(withdrawal1.amount());
        assertThat(actual.get(0).withdrawals().get(0).date()).isEqualTo(withdrawal1.date());

        assertThat(actual.get(0).withdrawals().get(1).id()).isEqualTo(withdrawal2.id());
        assertThat(actual.get(0).withdrawals().get(1).timeDepositId()).isEqualTo(withdrawal2.timeDepositId());
        assertThat(actual.get(0).withdrawals().get(1).amount()).isEqualTo(withdrawal2.amount());
        assertThat(actual.get(0).withdrawals().get(1).date()).isEqualTo(withdrawal2.date());

    }

    @Test
    void getTimeDeposits_returnsEmptyList() {
        when(timeDepositService.getTimeDeposits()).thenReturn(Collections.emptyList());

        List<TimeDepositWithWithdrawalsDto> actual = underTest.getTimeDeposits();

        assertThat(actual).isEmpty();
    }

    @Test
    void saveInterests_savesAndReturnsMappedTimeDeposits() {
        TimeDepositV2 updated1 = new TimeDepositV2(1, BASIC.name(), BigDecimal.valueOf(1000.83), 45);
        TimeDepositV2 updated2 = new TimeDepositV2(2, BASIC.name(), BigDecimal.valueOf(2001.66), 90);
        when(timeDepositService.updateTimeDepositBalances()).thenReturn(List.of(updated1, updated2));

        List<TimeDepositDto> actual = underTest.saveInterests();

        assertThat(actual).hasSize(2);

        assertThat(actual.get(0).id()).isEqualTo(updated1.id());
        assertThat(actual.get(0).planType()).isEqualTo(updated1.planType());
        assertThat(actual.get(0).balance()).isEqualTo(updated1.balance());
        assertThat(actual.get(0).days()).isEqualTo(updated1.days());

        assertThat(actual.get(1).id()).isEqualTo(updated2.id());
        assertThat(actual.get(1).planType()).isEqualTo(updated2.planType());
        assertThat(actual.get(1).balance()).isEqualTo(updated2.balance());
        assertThat(actual.get(1).days()).isEqualTo(updated2.days());
    }

    @Test
    void saveInterests_returnsEmptyList() {
        when(timeDepositService.updateTimeDepositBalances()).thenReturn(Collections.emptyList());

        List<TimeDepositDto> actual = underTest.saveInterests();

        assertThat(actual).isEmpty();
    }
}
