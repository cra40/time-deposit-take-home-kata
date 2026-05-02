package org.ikigaidigital.adapter.in.dto;

import java.math.BigDecimal;
import java.util.List;

public record TimeDepositWithWithdrawalsDto (
        Integer id,
        String planType,
        BigDecimal balance,
        Integer days,
        List<WithdrawalDto> withdrawals
) {
}
