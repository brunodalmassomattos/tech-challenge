package br.com.fiap.entrega.domain.repository;

import br.com.fiap.entrega.domain.entity.Entrega;

import java.util.List;
import java.util.UUID;

public interface EntregaRepository {

    Entrega buscarPorId(UUID id);

    Entrega buscarPorCodigoRastreio(String codigoRastreio);

    Entrega persistir(Entrega entrega);
}
