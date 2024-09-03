package br.com.desafio.api;


import br.com.desafio.api.dto.PaymentDto;
import br.com.desafio.domain.usecase.ConfirmPaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PaymentController {

    private final ConfirmPaymentUseCase confirmPaymentUseCase;

    @PutMapping(path = "/api/payment")
    public ResponseEntity<PaymentDto> setPayment(PaymentDto request) {

        return ResponseEntity.status(HttpStatus.OK).body(PaymentDto.builder().build());
    }
}
