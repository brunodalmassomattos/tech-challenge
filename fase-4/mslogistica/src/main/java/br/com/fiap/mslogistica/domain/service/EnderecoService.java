package br.com.fiap.mslogistica.domain.service;

import br.com.fiap.mslogistica.domain.entity.Endereco;

import java.util.UUID;

public interface EnderecoService {
    Endereco buscarEnderecoPorId(UUID id);
}
