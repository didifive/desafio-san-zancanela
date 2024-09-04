package br.com.desafio.infraestructure.repository.jpa;

import br.com.desafio.infraestructure.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientJpaRepository extends JpaRepository<ClientEntity, String> {
}
