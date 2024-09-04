package br.com.desafio.domain.model;

import java.math.BigDecimal;

public record ChargeModel(
        String id,
        BigDecimal originalAmount,
        String clientId
) {
}
