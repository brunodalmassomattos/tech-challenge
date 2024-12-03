package br.com.fiap.mslogistica.domain.repository;

import br.com.fiap.mslogistica.domain.entity.Lote;

import java.util.Optional;
import java.util.UUID;

public interface LoteRepository {

    Lote buscarPorId(UUID id);

    Optional<Lote> buscarPorCep(String cep, String situacao);

    Lote persistir(Lote lote);
}
