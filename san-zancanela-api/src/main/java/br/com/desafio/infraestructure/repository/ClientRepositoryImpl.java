package br.com.desafio.infraestructure.repository;

import br.com.desafio.domain.model.ClientModel;
import br.com.desafio.domain.repository.ClientRepository;
import br.com.desafio.infraestructure.entity.ClientEntity;
import br.com.desafio.infraestructure.repository.jpa.ClientJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
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
