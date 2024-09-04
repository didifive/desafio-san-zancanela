package br.com.desafio.infraestructure.repository.jpa;

import br.com.desafio.infraestructure.entity.ChargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeJpaRepository extends JpaRepository<ChargeEntity, String> {

}
