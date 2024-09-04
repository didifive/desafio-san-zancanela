package br.com.desafio.domain.repository;

import br.com.desafio.domain.model.ChargeModel;

import java.util.List;
import java.util.Optional;

public interface ChargeRepository {

    Optional<ChargeModel> findById(String id);

    List<ChargeModel> findAllByClientId(String clientId);
}
