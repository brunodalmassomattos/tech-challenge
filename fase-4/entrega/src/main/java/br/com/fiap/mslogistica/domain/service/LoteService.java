package br.com.fiap.entrega.domain.service;

import br.com.fiap.entrega.application.dto.LocalizacaoDto;
import br.com.fiap.entrega.application.dto.LoteEntregaResponseDto;
import br.com.fiap.entrega.domain.entity.Lote;

import java.util.Optional;
import java.util.UUID;

public interface LoteService {

    Optional<Lote> buscarLotePorCep(String cep);

    LoteEntregaResponseDto atualizarLocalizacao(UUID id, LocalizacaoDto localizacaoDto);

    LoteEntregaResponseDto atribuirTransportadora(UUID id, String trasportadora);

    LoteEntregaResponseDto atualizarSituacao(UUID id, String situacao);

    LoteEntregaResponseDto buscarEntregas(UUID id);
}
