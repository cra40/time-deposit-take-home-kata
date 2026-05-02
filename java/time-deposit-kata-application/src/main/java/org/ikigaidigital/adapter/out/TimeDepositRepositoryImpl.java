package org.ikigaidigital.adapter.out;

import org.ikigaidigital.adapter.out.entity.TimeDepositEntity;
import org.ikigaidigital.domain.TimeDepositV2;
import org.ikigaidigital.port.out.TimeDepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TimeDepositRepositoryImpl implements TimeDepositRepository {

    private final TimeDepositJPARepository timeDepositJPARepository;

    @Autowired
    public TimeDepositRepositoryImpl(TimeDepositJPARepository timeDepositJPARepository) {
        this.timeDepositJPARepository = timeDepositJPARepository;
    }

    @Override
    public List<TimeDepositV2> findAll() {
        return timeDepositJPARepository.findAll()
                .stream()
                .map(this::toTimeDepositV2)
                .toList();
    }

    @Override
    public List<TimeDepositV2> saveAll(List<TimeDepositV2> timeDeposits) {
        return timeDepositJPARepository.saveAll(
                timeDeposits
                        .stream()
                        .map(this::toTimeDepositEntity)
                        .toList()
        ).stream().map(this::toTimeDepositV2).toList();
    }

    private TimeDepositV2 toTimeDepositV2(TimeDepositEntity entity) {
        return new TimeDepositV2(
                entity.getId(),
                entity.getPlanType(),
                entity.getBalance(),
                entity.getDays()
        );
    }

    private TimeDepositEntity toTimeDepositEntity(TimeDepositV2 timeDeposit) {
        return new TimeDepositEntity(
                timeDeposit.id(),
                timeDeposit.planType(),
                timeDeposit.balance(),
                timeDeposit.days()
        );
    }
}
