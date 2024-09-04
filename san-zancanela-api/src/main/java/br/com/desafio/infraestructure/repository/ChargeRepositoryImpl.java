package br.com.desafio.infraestructure.repository;

import br.com.desafio.domain.model.ChargeModel;
import br.com.desafio.domain.repository.ChargeRepository;
import br.com.desafio.infraestructure.entity.ChargeEntity;
import br.com.desafio.infraestructure.repository.jpa.ChargeJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChargeRepositoryImpl implements ChargeRepository {

    private final ChargeJpaRepository chargeJpaRepository;

    public ChargeRepositoryImpl(ChargeJpaRepository chargeJpaRepository) {
        this.chargeJpaRepository = chargeJpaRepository;
    }

    @Override
    public Optional<ChargeModel> findById(String id) {
        return chargeJpaRepository
                .findById(id)
                .map(ChargeEntity::toModel);
    }

    @Override
    public List<ChargeModel> findAllByClientId(String clientId) {
        return chargeJpaRepository.findAllByClientId(clientId)
                .stream().map(ChargeEntity::toModel).toList();
    }
}
