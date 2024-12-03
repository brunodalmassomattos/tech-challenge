package br.com.fiap.mslogistica.domain.repository;

import br.com.fiap.mslogistica.domain.entity.Endereco;

import java.util.UUID;

public interface EnderecoRepository {
    Endereco buscarPorId(UUID id);
}
