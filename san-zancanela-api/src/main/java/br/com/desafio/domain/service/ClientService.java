package br.com.desafio.domain.service;

import br.com.desafio.domain.exception.EntityNotFoundException;
import br.com.desafio.domain.model.ClientModel;
import br.com.desafio.domain.repository.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public ClientModel findById(String id) {
        return repository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Client not found"));
    }
}
