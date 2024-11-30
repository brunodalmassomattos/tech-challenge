package br.com.fiap.entrega.domain.service;

import br.com.fiap.entrega.application.dto.*;

import java.util.UUID;

public interface EntregaService {

    EntregaResponseDto criarEntrega(UUID enderecoUsuarioId);

    EntregaResponseDto atualizarSituacao(UUID id, String situacao);

    EntregaResponseDto atribuirEntregador(UUID id, String entregador);

    EntregaResponseDto buscarEntregaPorCodigoRastreio(String codigoRastreio);

    EntregaResponseDto atualizarLocalizacaoEntrega(UUID id, LocalizacaoDto localizacao);

}
