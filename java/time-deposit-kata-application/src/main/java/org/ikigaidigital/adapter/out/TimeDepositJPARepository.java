package org.ikigaidigital.adapter.out;

import org.ikigaidigital.adapter.out.entity.TimeDepositEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeDepositJPARepository extends JpaRepository<TimeDepositEntity, Integer> {
}
