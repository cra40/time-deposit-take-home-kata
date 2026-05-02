package org.ikigaidigital.adapter.out;

import org.ikigaidigital.adapter.out.entity.TimeDepositEntity;
import org.ikigaidigital.domain.TimeDepositV2;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeDepositRepositoryImplTest {

    @Mock
    private TimeDepositJPARepository timeDepositJPARepository;

    @InjectMocks
    private TimeDepositRepositoryImpl underTest;

    @Test
    void findAll_returnsMappedTimeDeposits() {
        List<TimeDepositEntity> entities = List.of(
                new TimeDepositEntity(1, "BASIC", BigDecimal.valueOf(1000.00), 45),
                new TimeDepositEntity(2, "STUDENT", BigDecimal.valueOf(2000.00), 90)
        );
        when(timeDepositJPARepository.findAll()).thenReturn(entities);

        List<TimeDepositV2> result = underTest.findAll();

        assertThat(result).containsExactly(
                new TimeDepositV2(1, "BASIC", BigDecimal.valueOf(1000.00), 45),
                new TimeDepositV2(2, "STUDENT", BigDecimal.valueOf(2000.00), 90)
        );
    }

    @Test
    void findAll_returnsEmptyListWhenNoDepositsExist() {
        when(timeDepositJPARepository.findAll()).thenReturn(List.of());

        List<TimeDepositV2> result = underTest.findAll();

        assertThat(result).isEmpty();
    }

    @Test
    void save_All_mapsToEntitiesAndDelegatesToJpaRepository() {
        List<TimeDepositV2> deposits = List.of(
                new TimeDepositV2(1, "BASIC", BigDecimal.valueOf(1000.00), 45),
                new TimeDepositV2(2, "STUDENT", BigDecimal.valueOf(2000.00), 90)
        );
        when(timeDepositJPARepository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<TimeDepositV2> saved = underTest.saveAll(deposits);

        assertThat(saved).hasSize(2);
        assertEquals(saved.get(0), deposits.get(0));
        assertEquals(saved.get(1), deposits.get(1));
    }

    private void assertEquals(TimeDepositV2 actual, TimeDepositV2 expected) {
        assertThat(actual.id()).isEqualTo(expected.id());
        assertThat(actual.planType()).isEqualTo(expected.planType());
        assertThat(actual.balance()).isEqualTo(expected.balance());
        assertThat(actual.days()).isEqualTo(expected.days());
    }
}
