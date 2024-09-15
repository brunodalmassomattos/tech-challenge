package br.com.fiap.level3.domain.restaurante.core;

import br.com.fiap.level3.domain.exception.ControllerNotFoundException;
import br.com.fiap.level3.domain.restaurante.core.domain.model.endereco.Endereco;
import br.com.fiap.level3.domain.restaurante.core.domain.model.restaurante.Restaurante;
import br.com.fiap.level3.domain.restaurante.core.ports.outcoming.RestauranteDatabase;

import java.util.Optional;
import java.util.UUID;

public class EnderecoFacade {

    private final RestauranteDatabase database;

    public EnderecoFacade(RestauranteDatabase database) {
        this.database = database;
    }

    public void alterEndereco(UUID idEndereco, Endereco novoEndereco) {
        Endereco enderecoSalvo = database.getEnderecoById(idEndereco)
                .orElseThrow(() -> new ControllerNotFoundException("Endereço não encontrado!"));

        enderecoSalvo.setCep(novoEndereco.getCep() != null ? novoEndereco.getCep() : enderecoSalvo.getCep());
        enderecoSalvo.setLogradouro(novoEndereco.getLogradouro() != null ? novoEndereco.getLogradouro() : enderecoSalvo.getLogradouro());
        enderecoSalvo.setNumero(novoEndereco.getNumero() != null ? novoEndereco.getNumero() : enderecoSalvo.getNumero());
        enderecoSalvo.setBairro(novoEndereco.getBairro() != null ? novoEndereco.getBairro() : enderecoSalvo.getBairro());
        enderecoSalvo.setCidade(novoEndereco.getCidade() != null ? novoEndereco.getCidade() : enderecoSalvo.getCidade());
        enderecoSalvo.setEstado(novoEndereco.getEstado() != null ? novoEndereco.getEstado() : enderecoSalvo.getEstado());

        database.updateEndereco(enderecoSalvo);
    }
}

