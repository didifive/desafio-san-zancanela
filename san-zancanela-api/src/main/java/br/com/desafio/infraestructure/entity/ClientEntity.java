package br.com.desafio.infraestructure.entity;

import br.com.desafio.domain.model.ClientModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    private String id;
    private String name;


    public ClientModel toModel() {
        return new ClientModel(
                getId()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
