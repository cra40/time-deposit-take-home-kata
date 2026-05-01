package org.ikigaidigital.domain;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record TimeDepositV2 (
        Integer id,
        @NotNull String planType,
        @NotNull BigDecimal balance,
        @NotNull Integer days
) {}
