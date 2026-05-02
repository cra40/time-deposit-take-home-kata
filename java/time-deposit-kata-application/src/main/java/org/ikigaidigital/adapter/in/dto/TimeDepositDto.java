package org.ikigaidigital.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "A time deposit with its current balance and elapsed days")
public record TimeDepositDto(
        @Schema(description = "Unique identifier of the time deposit", example = "1") Integer id,
        @Schema(description = "Plan type determining the interest rate", example = "PLAN_A") String planType,
        @Schema(description = "Current balance including accrued interest", example = "10500.00") BigDecimal balance,
        @Schema(description = "Number of days elapsed since the deposit was opened", example = "30") Integer days
) {
}
