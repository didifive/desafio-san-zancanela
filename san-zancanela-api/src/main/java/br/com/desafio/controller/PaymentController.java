package br.com.desafio.controller;


import br.com.desafio.controller.dto.request.PaymentRequestDto;
import br.com.desafio.controller.dto.response.PaymentResponseDto;
import br.com.desafio.domain.usecase.ConfirmPaymentUseCase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentController {

    private final ConfirmPaymentUseCase service;

    public PaymentController(ConfirmPaymentUseCase service) {
        this.service = service;
    }

    @PutMapping(path = "/api/payment")
    public ResponseEntity<PaymentResponseDto> setPayment(PaymentRequestDto request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(PaymentResponseDto.toResponseDto(
                        service.confirm(request.toPaymentModel())));
    }
}
