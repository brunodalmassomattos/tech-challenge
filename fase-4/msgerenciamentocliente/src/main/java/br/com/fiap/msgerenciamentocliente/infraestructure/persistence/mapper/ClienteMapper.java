package br.com.fiap.msgerenciamentocliente.infraestructure.persistence.mapper;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Cliente;
import br.com.fiap.msgerenciamentocliente.infraestructure.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    private final EnderecoMapper enderecoMapper;

    public ClienteMapper(EnderecoMapper enderecoMapper) {
        this.enderecoMapper = enderecoMapper;
    }

    public Cliente toDomain(ClienteEntity entity) {
        if (entity == null) return null;
        Cliente cliente = new Cliente();
        cliente.setId(entity.getId());
        cliente.setNome(entity.getNome());
        cliente.setCpf(entity.getCpf());
        cliente.setDataNascimento(entity.getDataNascimento());
        cliente.setTelefone(entity.getTelefone());
        cliente.setEmail(entity.getEmail());
        cliente.setEndereco(enderecoMapper.toDomain(entity.getEndereco()));
        cliente.setEnderecoEntrega(enderecoMapper.toDomain(entity.getEnderecoEntrega()));
        return cliente;
    }

    public ClienteEntity toEntity(Cliente domain) {
        if (domain == null) return null;
        ClienteEntity entity = new ClienteEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setCpf(domain.getCpf());
        entity.setDataNascimento(domain.getDataNascimento());
        entity.setTelefone(domain.getTelefone());
        entity.setEmail(domain.getEmail());
        entity.setEndereco(enderecoMapper.toEntity(domain.getEndereco()));
        entity.setEnderecoEntrega(enderecoMapper.toEntity(domain.getEnderecoEntrega()));
        return entity;
    }
}