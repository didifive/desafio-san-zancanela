package br.com.desafio.domain.model;

import java.math.BigDecimal;

public record PaymentItemModel(String chargeId,
                               BigDecimal paymentValue,
                               PaymentStatus paymentStatus
) {
}
