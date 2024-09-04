package br.com.desafio.domain.usecase.impl;

import br.com.desafio.domain.model.*;
import br.com.desafio.domain.service.ChargeService;
import br.com.desafio.domain.service.ClientService;
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

    private ClientModel clientModel;
    private ChargeModel chargeModel;
    private ChargeModel chargeModel2;
    private ChargeModel chargeModel3;
    private PaymentModel paymentModel;


    @Mock
    private ChargeService chargeService;
    @Mock
    private ClientService clientService;


    @InjectMocks
    private ConfirmPaymentUseCaseImpl confirmPaymentService;

    @BeforeEach
    void setup() {
        clientModel = new ClientModel("clientId-123", "cliente");

        chargeModel = new ChargeModel(
                "charge-1",
                BigDecimal.valueOf(100.99)
        );

        chargeModel2 = new ChargeModel(
                "charge-2",
                BigDecimal.valueOf(5.99)
        );

        chargeModel3 = new ChargeModel(
                "charge-3",
                BigDecimal.valueOf(1000.00)
        );

        paymentModel = new PaymentModel(
                clientModel.id(),
                List.of(
                        new PaymentItemModel(chargeModel.id(), BigDecimal.valueOf(100.99), null),
                        new PaymentItemModel(chargeModel2.id(), BigDecimal.valueOf(59.90), null),
                        new PaymentItemModel(chargeModel3.id(), BigDecimal.valueOf(100.00), null)
                )
        );
    }

    @Test
    void testStatusPaymentWithTotalExcessAndPartialStatus() {
        when(clientService.findById(clientModel.id())).thenReturn(clientModel);
        when(chargeService.findById(chargeModel.id())).thenReturn(chargeModel);
        when(chargeService.findById(chargeModel2.id())).thenReturn(chargeModel2);
        when(chargeService.findById(chargeModel3.id())).thenReturn(chargeModel3);

        PaymentModel result = confirmPaymentService.confirm(paymentModel);

        assertAll("Assert that PaymentModel has a TOTAL, EXCESS and PARTIAL payment status",
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.TOTAL)),
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.EXCESS)),
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.PARTIAL))
        );

        verify(clientService, times(1)).findById(clientModel.id());
        verify(chargeService, times(3)).findById(anyString());

    }
}
