package org.ikigaidigital.dto;

import java.math.BigDecimal;
import java.util.List;

public record TimeDepositDto (
        Integer id,
        String planType,
        BigDecimal balance,
        Integer days,
        List<WithdrawalDto> withdrawals
) {
}
