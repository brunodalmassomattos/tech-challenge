package br.com.fiap.mslogistica.domain.repository;

import br.com.fiap.mslogistica.domain.entity.Entrega;

import java.util.UUID;

public interface EntregaRepository {

    Entrega buscarPorId(UUID id);

    Entrega buscarPorCodigoRastreio(String codigoRastreio);

    Entrega persistir(Entrega entrega);
}
