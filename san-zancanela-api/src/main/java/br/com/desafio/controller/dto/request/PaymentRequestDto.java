package br.com.desafio.controller.dto.request;

import br.com.desafio.domain.model.PaymentModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PaymentRequestDto(
        @NotBlank
        @JsonProperty("client_id")
        String clientId,
        @NotNull
        @JsonProperty("payment_items")

        List<PaymentItemRequestDto> paymentItems) {
    public PaymentModel toPaymentModel() {
        return new PaymentModel(
                clientId(),
                paymentItems().stream()
                        .map(PaymentItemRequestDto::toPaymentItemModel)
                        .toList()
        );
    }

}
