package br.com.desafio.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PaymentDto(@JsonProperty("client_id")
                         String clientId,
                         @JsonProperty("payment_items")
                         List<PaymentItemDto> paymentItems) {

}
