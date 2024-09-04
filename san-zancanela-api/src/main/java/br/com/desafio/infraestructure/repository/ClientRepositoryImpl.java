package br.com.desafio.infraestructure.repository;

import br.com.desafio.domain.model.ClientModel;
import br.com.desafio.domain.repository.ClientRepository;
import br.com.desafio.infraestructure.entity.ClientEntity;
import br.com.desafio.infraestructure.repository.jpa.ClientJpaRepository;

import java.util.List;
import java.util.Optional;

public class ClientRepositoryImpl implements ClientRepository {

    private final ClientJpaRepository clientJpaRepository;

    public ClientRepositoryImpl(ClientJpaRepository clientJpaRepository) {
        this.clientJpaRepository = clientJpaRepository;
    }

    @Override
    public Optional<ClientModel> findById(String id) {
        return clientJpaRepository
                .findById(id)
                .map(ClientEntity::toModel);
    }
}
