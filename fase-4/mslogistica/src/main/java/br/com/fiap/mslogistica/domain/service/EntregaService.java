package br.com.fiap.mslogistica.domain.service;

import br.com.fiap.mslogistica.application.dto.*;

import java.util.UUID;

public interface EntregaService {

    void criarEntrega(PedidoConsumerDto pedidoConsumerDto);

    EntregaResponseDto atualizarSituacao(UUID id, String situacao);

    EntregaResponseDto atribuirEntregador(UUID id, String entregador);

    EntregaResponseDto buscarEntregaPorCodigoRastreio(String codigoRastreio);

    EntregaResponseDto atualizarLocalizacaoEntrega(UUID id, LocalizacaoDto localizacao);

}
