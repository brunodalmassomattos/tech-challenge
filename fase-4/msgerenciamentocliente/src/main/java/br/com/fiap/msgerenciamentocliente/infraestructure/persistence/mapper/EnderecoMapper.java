package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.mapper;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.entity.EnderecoEntity;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {
    public Endereco toDomain(EnderecoEntity entity) {
        if (entity == null) return null;
        Endereco endereco = new Endereco();
        endereco.setId(entity.getId());
        endereco.setRua(entity.getRua());
        endereco.setBairro(entity.getBairro());
        endereco.setComplemento(entity.getComplemento());
        endereco.setNumero(entity.getNumero());
        endereco.setCidade(entity.getCidade());
        endereco.setEstado(entity.getEstado());
        endereco.setCep(entity.getCep());
        return endereco;
    }

    public EnderecoEntity toEntity(Endereco domain) {
        if (domain == null) return null;
        EnderecoEntity entity = new EnderecoEntity();
        entity.setId(domain.getId());
        entity.setRua(domain.getRua());
        entity.setBairro(domain.getBairro());
        entity.setComplemento(domain.getComplemento());
        entity.setNumero(domain.getNumero());
        entity.setCidade(domain.getCidade());
        entity.setEstado(domain.getEstado());
        entity.setCep(domain.getCep());
        return entity;
    }
}