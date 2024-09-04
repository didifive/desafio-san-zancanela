package br.com.desafio.infraestructure.service;

import br.com.desafio.domain.exception.EntityNotFoundException;
import br.com.desafio.infraestructure.repository.ChargeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ChargeService {

    private final ChargeRepository repository;

    public ChargeService(ChargeRepository repository) {
        this.repository = repository;
    }

    public BigDecimal getOriginalAmountFromId(String id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Charge not found"))
                .getOriginalAmount();
    }
}
