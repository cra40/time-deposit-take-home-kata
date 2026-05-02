package org.ikigaidigital.adapter.in.dto;

import java.math.BigDecimal;
import java.util.Date;

public record WithdrawalDto (Integer id,
                            Integer timeDepositId,
                            BigDecimal amount,
                            Date date) {
}
