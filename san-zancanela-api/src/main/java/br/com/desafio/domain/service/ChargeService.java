package br.com.desafio.domain.service;

import br.com.desafio.domain.exception.EntityNotFoundException;
import br.com.desafio.domain.model.ChargeModel;
import br.com.desafio.domain.repository.ChargeRepository;
import org.springframework.stereotype.Service;

@Service
public class ChargeService {

    private final ChargeRepository repository;

    public ChargeService(ChargeRepository repository) {
        this.repository = repository;
    }

    public ChargeModel findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Charge not found"));
    }
}
