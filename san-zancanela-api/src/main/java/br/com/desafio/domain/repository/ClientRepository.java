package br.com.desafio.domain.repository;

import br.com.desafio.domain.model.ClientModel;

import java.util.Optional;

public interface ClientRepository {

    Optional<ClientModel> findById(String id);
}
