package org.ikigaidigital.adapter.in.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.Date;

@Schema(description = "A withdrawal made against a time deposit")
public record WithdrawalDto(
        @Schema(description = "Unique identifier of the withdrawal", example = "1") Integer id,
        @Schema(description = "Identifier of the parent time deposit", example = "1") Integer timeDepositId,
        @Schema(description = "Amount withdrawn", example = "500.00") BigDecimal amount,
        @Schema(description = "Date the withdrawal was made") Date date
) {
}
