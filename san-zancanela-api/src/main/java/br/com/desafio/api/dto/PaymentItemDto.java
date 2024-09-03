package br.com.desafio.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PaymentItemDto(@JsonProperty("payment_id")
                             String paymentId,
                             @JsonProperty("payment_value")
                             BigDecimal paymentValue,
                             @JsonProperty("payment_status")
                             String paymentStatus) {

}
