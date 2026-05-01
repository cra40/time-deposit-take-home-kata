package org.ikigaidigital.domain;

import java.math.BigDecimal;

public record TimeDepositV2 (
        Integer id,
        String planType,
        BigDecimal balance,
        Integer days
) {}
