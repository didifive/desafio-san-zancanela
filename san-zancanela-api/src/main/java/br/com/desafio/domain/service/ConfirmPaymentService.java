package br.com.desafio.domain.service;

import br.com.desafio.domain.model.PaymentItemModel;
import br.com.desafio.domain.model.PaymentModel;
import br.com.desafio.domain.model.PaymentStatus;
import br.com.desafio.domain.usecase.ConfirmPaymentUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ConfirmPaymentService implements ConfirmPaymentUseCase {

    private final ChargeService chargeService;
    private final ClientService clientService;

    public ConfirmPaymentService(ChargeService chargeService, ClientService clientService) {
        this.chargeService = chargeService;
        this.clientService = clientService;
    }

    @Override
    @Transactional
    public PaymentModel confirm(PaymentModel paymentModel) {
        clientService.findById(paymentModel.clientId());

        List<PaymentItemModel> paymentItemModelList =
                paymentModel.paymentItems().stream()
                        .map(item -> {
                            BigDecimal originalAmount = chargeService.findById(item.chargeId())
                                    .originalAmount();

                            PaymentStatus paymentStatus = paymentStatus(item.paymentValue(), originalAmount);

                            return new PaymentItemModel(
                                    item.chargeId(),
                                    item.paymentValue(),
                                    paymentStatus
                            );
                        })
                        .toList();

        return new PaymentModel(paymentModel.clientId(), paymentItemModelList);

    }

    private PaymentStatus paymentStatus (BigDecimal paymentValue, BigDecimal originalAmount) {
        return switch (paymentValue.compareTo(originalAmount)) {
            case -1 -> PaymentStatus.PARTIAL;
            case 0 -> PaymentStatus.TOTAL;
            case 1 -> PaymentStatus.EXCESS;
            default -> throw new IllegalStateException("Unexpected value: " + paymentValue);
        };
    }
}
