package br.com.desafio.infraestructure.entity;

import br.com.desafio.domain.model.ClientModel;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "clients")
public class ClientEntity {
    @Id
    private String id;
    private String name;

    public ClientEntity(String id) {
        this.id = id;
    }

    public ClientEntity(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ClientEntity(ClientModel model) {
        this(model.id(), model.name());
    }

    public ClientModel toModel() {
        return new ClientModel(
                getId(),
                getName()
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
