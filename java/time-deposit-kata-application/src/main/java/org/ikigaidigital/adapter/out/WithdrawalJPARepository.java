package org.ikigaidigital.adapter.out;

import org.ikigaidigital.adapter.out.entity.WithdrawalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalJPARepository extends JpaRepository<WithdrawalEntity, Integer> {

    List<WithdrawalEntity> findByTimeDepositId(Integer timeDepositId);
}
