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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmPaymentUseCaseImplTest {

    private ClientModel clientModel;
    private ChargeModel chargeModel;
    private ChargeModel chargeModel2;
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
                BigDecimal.valueOf(10.99),
                clientModel.id()
        );

        chargeModel2 = new ChargeModel(
                "charge-2",
                BigDecimal.valueOf(5.99),
                clientModel.id()
        );

        paymentModel = new PaymentModel(
                clientModel.id(),
                List.of(
                        new PaymentItemModel(chargeModel.id(), BigDecimal.valueOf(10.99), null),
                        new PaymentItemModel(chargeModel2.id(), BigDecimal.valueOf(5.99), null)
                )
        );
    }

    @Test
    void testStatusPaymentWithTotalStatus() {
        when(clientService.findById(clientModel.id())).thenReturn(clientModel);
        when(chargeService.findById(chargeModel.id())).thenReturn(chargeModel);
        when(chargeService.findById(chargeModel2.id())).thenReturn(chargeModel2);

        PaymentModel result = confirmPaymentService.confirm(paymentModel);

        assertAll("Assert status for all payments",
                () -> result.paymentItems()
                        .forEach(item ->
                                assertEquals(PaymentStatus.TOTAL, item.paymentStatus()))
        );

    }

    @Test
    void testStatusPaymentWithPartialAndTotalStatus() {
        PaymentModel paymentModelWithPartial = new PaymentModel(
                clientModel.id(),
                List.of(
                        new PaymentItemModel(chargeModel.id(), BigDecimal.valueOf(1.99), null),
                        new PaymentItemModel(chargeModel2.id(), BigDecimal.valueOf(5.99), null)
                )
        );

        when(clientService.findById(clientModel.id())).thenReturn(clientModel);
        when(chargeService.findById(chargeModel.id())).thenReturn(chargeModel);
        when(chargeService.findById(chargeModel2.id())).thenReturn(chargeModel2);

        PaymentModel result = confirmPaymentService.confirm(paymentModelWithPartial);

        assertAll("Assert that PaymentModel as a item with PARTIAL and other with TOTAL",
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.PARTIAL)),
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.TOTAL))
        );

    }

    @Test
    void testStatusPaymentWithExcessAndPartialStatus() {
        PaymentModel paymentModelWithPartial = new PaymentModel(
                clientModel.id(),
                List.of(
                        new PaymentItemModel(chargeModel.id(), BigDecimal.valueOf(500.99), null),
                        new PaymentItemModel(chargeModel2.id(), BigDecimal.valueOf(2.99), null)
                )
        );

        when(clientService.findById(clientModel.id())).thenReturn(clientModel);
        when(chargeService.findById(chargeModel.id())).thenReturn(chargeModel);
        when(chargeService.findById(chargeModel2.id())).thenReturn(chargeModel2);

        PaymentModel result = confirmPaymentService.confirm(paymentModelWithPartial);

        assertAll("Assert that PaymentModel as a item with PARTIAL and other with TOTAL",
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.EXCESS)),
                () -> assertTrue(result.paymentItems().stream()
                        .anyMatch(item -> item.paymentStatus() == PaymentStatus.PARTIAL))
        );

    }
}
