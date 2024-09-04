package br.com.desafio.controller.dto.response;

import br.com.desafio.domain.model.PaymentStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public record PaymentItemResponseDto(@JsonProperty("charge_id")
                                     String chargeId,
                                     @JsonProperty("payment_value")
                                     BigDecimal paymentValue,
                                     @JsonProperty("payment_status")
                                     PaymentStatus paymentStatus) {



}
