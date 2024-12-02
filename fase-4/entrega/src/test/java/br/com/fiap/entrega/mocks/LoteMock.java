package br.com.fiap.entrega.mocks;

import br.com.fiap.entrega.application.enumerator.SituacaoLoteEnum;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.entity.Lote;

import java.util.List;
import java.util.UUID;

public class LoteMock {

    public static Lote getLote() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.CRIADO.getValor())
                       .entregas(List.of(EntregaMock.getEntrega()))
                       .build();
    }

    public static Lote getLoteDespachado() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.DESPACHADO.getValor())
                       .entregas(List.of(EntregaMock.getEntrega()))
                       .build();
    }

    public static Lote getLoteComTrasportadora() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.EM_SEPARACAO.getValor())
                       .entregas(List.of(EntregaMock.getEntrega()))
                       .transportadora("Total Express")
                       .build();
    }

    public static Lote getLoteSemTransportadora() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.EM_SEPARACAO.getValor())
                       .entregas(List.of(EntregaMock.getEntrega()))
                       .build();
    }

    public static Lote getLoteChegadaEmFilial() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.ENTRADA_FILIAL.getValor())
                       .entregas(List.of(EntregaMock.getEntrega()))
                       .transportadora("Total Express")
                       .build();
    }

    public static Lote getLoteComProblema() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.PROBLEMA.getValor())
                       .entregas(List.of(EntregaMock.getEntregaComProblema()))
                       .transportadora("Total Express")
                       .build();
    }

    public static Lote getLoteEncontrado() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.CRIADO.getValor())
                       .entregas(List.of(Entrega.builder().build()))
                       .build();
    }

    public static Lote getLoteSemEntregas() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.CRIADO.getValor())
                       .entregas(List.of())
                       .build();
    }

    public static Lote getLoteComEntregasNull() {
        return Lote.builder()
                       .id(UUID.randomUUID())
                       .cepEntrega("15130-023")
                       .status(SituacaoLoteEnum.CRIADO.getValor())
                       .build();
    }
}
