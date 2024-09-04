package br.com.desafio.infraestructure.entity;

import br.com.desafio.domain.model.ChargeModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "charges")
public class ChargeEntity {
    @Id
    String id;
    BigDecimal originalAmount;
    @ManyToOne
    ClientEntity client;


    public ChargeModel toModel() {
        return new ChargeModel(
                getId(),
                getOriginalAmount(),
                getClient().getId()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public ClientEntity getClient() {
        return client;
    }

    public void setClient(ClientEntity client) {
        this.client = client;
    }

}
