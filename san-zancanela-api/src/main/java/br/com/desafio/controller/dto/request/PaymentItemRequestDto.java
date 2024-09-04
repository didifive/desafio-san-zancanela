package br.com.desafio.controller.dto.request;

import br.com.desafio.domain.model.PaymentItemModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentItemRequestDto(
        @NotBlank
        @JsonProperty("charge_id")
        String chargeId,
        @NotNull
        @JsonProperty("payment_value")
        BigDecimal paymentValue) {

    public PaymentItemModel toPaymentItemModel() {
        return new PaymentItemModel(
                chargeId(),
                paymentValue(),
                null
        );
    }

}
