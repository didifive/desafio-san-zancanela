package br.com.desafio.infraestructure.service;

import br.com.desafio.domain.model.PaymentItemModel;
import br.com.desafio.domain.usecase.PaymentQueueUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

@Component
public class SendToSQS implements PaymentQueueUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendToSQS.class);

    private final SqsClient sqsClient;
    @Value("${aws.region}")
    private String region;
    @Value("${san-zancanela-api.aws.accountId}")
    private String accountId;

    private final String baseUrl = "https://sqs."
            + region
            + ".amazonaws.com/"
            + accountId
            + "/";

    public SendToSQS(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Override
    public void enqueuePayment(PaymentItemModel payment) {
        String queueName = switch (payment.paymentStatus()) {
            case TOTAL -> "total-payment-queue";
            case PARTIAL -> "partial-payment-queue";
            case EXCESS -> "excess-payment-queue";
        };

        sendMessage(payment, queueName);
    }

    private void sendMessage(PaymentItemModel paymentItem, String queueName) {
        try {
            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(baseUrl + queueName)
                    .messageBody(paymentItem.toString())
                    .build();

            sqsClient.sendMessage(request);
        } catch (Exception e) {
            LOGGER.error("Error sending message [{}] to queue [{}]", paymentItem, queueName, e);
        }
    }
}
