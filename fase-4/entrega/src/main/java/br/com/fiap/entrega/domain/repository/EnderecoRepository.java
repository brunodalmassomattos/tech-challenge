package br.com.fiap.entrega.domain.repository;

import br.com.fiap.entrega.domain.entity.Endereco;

import java.util.UUID;

public interface EnderecoRepository {
    Endereco buscarPorId(UUID id);
}
