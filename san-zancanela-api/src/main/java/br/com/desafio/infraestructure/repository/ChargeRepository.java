package br.com.desafio.infraestructure.repository;

import br.com.desafio.infraestructure.entity.ChargeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChargeRepository extends JpaRepository<ChargeEntity, String> {

}
