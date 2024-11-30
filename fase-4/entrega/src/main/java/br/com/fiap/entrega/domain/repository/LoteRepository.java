package br.com.fiap.entrega.domain.repository;

import br.com.fiap.entrega.domain.entity.Lote;

import java.util.Optional;
import java.util.UUID;

public interface LoteRepository {

    Lote buscarPorId(UUID id);

    Optional<Lote> buscarPorCep(String cep, String situacao);

    Lote persistir(Lote lote);
}
