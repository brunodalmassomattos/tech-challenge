package br.com.fiap.entrega.mocks;

import br.com.fiap.entrega.application.enumerator.SituacaoEnum;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.entity.Lote;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;

public class EntregaMock {

    public static Entrega getEntrega() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.CRIADO.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .lote(Lote.builder().build())
                       .build();
    }

    public static Entrega getEntregaComProblema() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.PROBLEMA.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .lote(Lote.builder().build())
                       .build();
    }

    public static Entrega getEntregaCriada() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.CRIADO.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .lote(LoteMock.getLoteEncontrado())
                       .build();
    }

    public static Entrega getEntregaRecebidaEmFilial() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.ENTRADA_FILIAL.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .entregador("Matheus")
                       .lote(LoteMock.getLoteEncontrado())
                       .build();
    }

    public static Entrega getEntregaEmRotaDeEntrega() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.EM_ROTA_ENTREGA.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .entregador("Matheus")
                       .lote(LoteMock.getLoteEncontrado())
                       .build();
    }

    public static Entrega getEntregaJaEntregue() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.ENTREGUE.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .entregador("Matheus")
                       .dataHoraEntrega(Timestamp.from(Instant.now()))
                       .lote(LoteMock.getLoteEncontrado())
                       .build();
    }

    public static Entrega getEntregaSemEntregadorAtribuido() {
        return Entrega.builder()
                       .id(UUID.randomUUID())
                       .codigoRastreio("ENT2024112501380610266960344023584573")
                       .status(SituacaoEnum.ENTRADA_FILIAL.getValor())
                       .endereco(EnderecoMock.getEndereco())
                       .lote(LoteMock.getLoteEncontrado())
                       .build();
    }
}
