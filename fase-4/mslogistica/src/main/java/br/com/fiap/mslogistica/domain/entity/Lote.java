package br.com.fiap.mslogistica.domain.entity;

import br.com.fiap.mslogistica.application.dto.*;
import br.com.fiap.mslogistica.application.enumerator.SituacaoLoteEnum;
import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "entrega.Lote")
@Table(name = "Lote")
public class Lote {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    private String cepEntrega;

    private String latitude;

    private String longitude;

    private String transportadora;

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.PERSIST,
            mappedBy = "lote")
    private List<Entrega> entregas;

    public static Lote criarLote(String cep) {
        return Lote.builder()
                       .cepEntrega(cep)
                       .status(SituacaoLoteEnum.CRIADO.getValor())
                       .build();
    }

    public static LoteEntregaResponseDto toLoteEntregaResponseDto(Lote lote) {
        return LoteEntregaResponseDto.builder()
                       .id(lote.id)
                       .trasportadora(lote.transportadora)
                       .situacao(SituacaoLoteEnum.getSituacaoLoteEnum(lote.status))
                       .localizacaoDto(criarLocalizacaoDto(lote.latitude, lote.longitude))
                       .entregasDtos(obterEntregasDtos(lote))
                       .build();
    }

    public static void atualizarSituacao(Lote lote, String situacao) {
        validarSituacao(lote, situacao);
        lote.status = situacao;
    }

    public static void atribuirTransportadora(Lote lote, String transportadora) {
        validarAlteracaoTransportadora(lote);
        lote.transportadora = transportadora;
    }

    public static Lote atualizarLocalizacao(Lote lote, LocalizacaoDto localizacaoDto) {
        lote.setLatitude(localizacaoDto.latitude());
        lote.setLongitude(localizacaoDto.longitude());
        return lote;
    }

    private static List<EntregaLoteResponseDto> obterEntregasDtos(Lote lote) {
        if (Objects.isNull(lote.entregas) || lote.entregas.isEmpty()) {
            throw new ControllerNotFoundException(
                    String.format("Não existe entregas para este lote: %s", lote.getId().toString()));
        }
        return lote.entregas.stream()
                       .map(Entrega::toEntregaLoteResponseDto)
                       .toList();
    }

    private static LocalizacaoDto criarLocalizacaoDto(String longitude, String latitude) {
        return new LocalizacaoDto(latitude, longitude);
    }

    private static void validarAlteracaoTransportadora(Lote lote) {
        if (SituacaoLoteEnum.getSituacaoLoteEnum(lote.status).ordinal() >= 3) {
            throw new ControllerNotFoundException(
                    String.format("Não é permitido alterar transportadora com lote nas situações: %s ou %s",
                            SituacaoLoteEnum.ENTRADA_FILIAL.getValor(), SituacaoLoteEnum.DESPACHADO.getValor()));
        }

        if (Objects.nonNull(lote.transportadora)) {
            throw new ControllerNotFoundException(
                    String.format("Já existe transportadora atribuída ao lote: %s", lote.id));
        }
    }

    private static void validarSituacao(Lote lote, String situacao) {
        SituacaoLoteEnum situacaoLoteEnum = SituacaoLoteEnum.getSituacaoLoteEnum(situacao);
        validarStatusFinal(lote);
        validarStatusAlteradoSejaProximo(SituacaoLoteEnum.getSituacaoLoteEnum(lote.status), situacaoLoteEnum);
        validarTransportadoraAtruibuida(lote, situacaoLoteEnum);
    }

    private static void validarStatusFinal(Lote lote) {
        if (validarStatusIgual(SituacaoLoteEnum.getSituacaoLoteEnum(lote.status), SituacaoLoteEnum.ENTRADA_FILIAL)) {
            throw new ControllerNotFoundException(String.format("Não é permitido alterar situação, quando lote se encontra na situação: %s", lote.status));
        }
    }

    private static boolean validarStatusIgual(SituacaoLoteEnum situacaoGravada, SituacaoLoteEnum situacaoLoteEnum) {
        return situacaoLoteEnum.getValor().equals(situacaoGravada.getValor());
    }

    private static void validarStatusAlteradoSejaProximo(SituacaoLoteEnum situacaoGravada, SituacaoLoteEnum situacaoLoteEnum) {
        if (situacaoGravada.ordinal() == 0 || situacaoLoteEnum.ordinal() == 0) {
            return;
        }

        if (situacaoGravada.ordinal() != (situacaoLoteEnum.ordinal() - 1)) {
            throw new ControllerNotFoundException(
                    String.format("A situação: %s não corresponde a próxima etapa", situacaoLoteEnum.getValor()));
        }
    }

    private static void validarTransportadoraAtruibuida(Lote lote, SituacaoLoteEnum situacaoLoteEnum) {
        if (Objects.isNull(lote.transportadora) && situacaoLoteEnum.ordinal() >= 3) {
            throw new ControllerNotFoundException(
                    String.format("Não é permitido avançar situação para %s sem transportadora atribuída", situacaoLoteEnum.getValor()));
        }
    }
}
