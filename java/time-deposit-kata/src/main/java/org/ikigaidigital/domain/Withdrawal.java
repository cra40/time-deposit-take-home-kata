package org.ikigaidigital.domain;

import java.math.BigDecimal;
import java.util.Date;

public record Withdrawal(Integer id,
                         Integer timeDepositId,
                         BigDecimal amount,
                         Date date) {

}
