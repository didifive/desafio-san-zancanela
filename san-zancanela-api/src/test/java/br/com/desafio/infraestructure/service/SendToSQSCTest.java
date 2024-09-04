package br.com.desafio.infraestructure.service;

import br.com.desafio.domain.model.PaymentItemModel;
import br.com.desafio.domain.model.PaymentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SendToSQSCTest {

    @Mock
    private SqsClient sqsClient;

    @InjectMocks
    private SendToSQS sendToSQS;

    private PaymentItemModel paymentItemModel;

    @BeforeEach
    public void setup() {
        paymentItemModel = new PaymentItemModel(
                "1",
                BigDecimal.TEN,
                PaymentStatus.TOTAL
        );
    }

    @Test
    void testEnqueuePayment() {
        sendToSQS.enqueuePayment(paymentItemModel);

        verify(sqsClient).sendMessage(any(SendMessageRequest.class));
    }
}
