package br.com.desafio.domain.usecase.impl;

import br.com.desafio.domain.model.*;
import br.com.desafio.domain.usecase.PaymentQueueUseCase;
import br.com.desafio.infraestructure.entity.ChargeEntity;
import br.com.desafio.infraestructure.entity.ClientEntity;
import br.com.desafio.infraestructure.service.ChargeService;
import br.com.desafio.infraestructure.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ConfirmPaymentUseCaseImplTest {

    private ClientEntity clientEntity;
    private ChargeEntity chargeEntity;
    private ChargeEntity chargeEntity2;
    private ChargeEntity chargeEntity3;
    private PaymentModel paymentModel;


    @Mock
    private ChargeService chargeService;
    @Mock
    private ClientService clientService;
    @Mock
    private PaymentQueueUseCase sendToExternalQueue;


    @InjectMocks
    private ConfirmPaymentUseCaseImpl confirmPaymentService;

    @BeforeEach
    void setup() {
        clientEntity = new ClientEntity();
        clientEntity.setId("client-1");

        chargeEntity = new ChargeEntity();
        chargeEntity.setId("charge-1");
        chargeEntity.setOriginalAmount(BigDecimal.valueOf(100.99));

        chargeEntity2 = new ChargeEntity();
        chargeEntity2.setId("charge-2");
        chargeEntity2.setOriginalAmount(BigDecimal.valueOf(5.99));

        chargeEntity3 = new ChargeEntity();
        chargeEntity3.setId("charge-3");
        chargeEntity3.setOriginalAmount(BigDecimal.valueOf(1000.00));

        paymentModel = new PaymentModel(
                clientEntity.getId(),
                List.of(
                        new PaymentItemModel(chargeEntity.getId(), BigDecimal.valueOf(100.99), null),
                        new PaymentItemModel(chargeEntity2.getId(), BigDecimal.valueOf(59.90), null),
                        new PaymentItemModel(chargeEntity3.getId(), BigDecimal.valueOf(100.00), null)
                )
        );
    }

    @Test
    void testStatusPaymentWithTotalExcessAndPartialStatus() {
        doNothing().when(clientService).findById(clientEntity.getId());
        doNothing().when(sendToExternalQueue).send(any(PaymentItemModel.class));

        when(chargeService.getOriginalAmountFromId(chargeEntity.getId()))
                .thenReturn(chargeEntity.getOriginalAmount());

        when(chargeService.getOriginalAmountFromId(chargeEntity2.getId()))
                .thenReturn(chargeEntity2.getOriginalAmount());

        when(chargeService.getOriginalAmountFromId(chargeEntity3.getId()))
                .thenReturn(chargeEntity3.getOriginalAmount());

        PaymentModel result = confirmPaymentService.confirm(paymentModel);

        assertAll("Assert that PaymentModel has a TOTAL, EXCESS and PARTIAL payment status",
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.TOTAL)),
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.EXCESS)),
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.PARTIAL))
        );

        verify(clientService, times(1)).findById(clientEntity.getId());
        verify(chargeService, times(3)).getOriginalAmountFromId(anyString());
        verify(sendToExternalQueue, times(3)).send(any(PaymentItemModel.class));

    }
}
