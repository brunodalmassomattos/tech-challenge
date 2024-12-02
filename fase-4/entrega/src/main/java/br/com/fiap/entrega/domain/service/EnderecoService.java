package br.com.fiap.entrega.domain.service;

import br.com.fiap.entrega.domain.entity.Endereco;

import java.util.UUID;

public interface EnderecoService {
    Endereco buscarEnderecoPorId(UUID id);
}
