package br.com.fiap.entrega.domain.entity;

import br.com.fiap.entrega.application.dto.EntregaLoteResponseDto;
import br.com.fiap.entrega.application.dto.EntregaResponseDto;
import br.com.fiap.entrega.application.dto.LocalizacaoDto;
import br.com.fiap.entrega.application.enumerator.SituacaoEnum;
import br.com.fiap.entrega.application.exception.ControllerNotFoundException;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Objects;
import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Entrega")
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String status;

    private String codigoRastreio;

    private Timestamp dataHoraEntrega;

    private String latitude;

    private String longitude;

    private String entregador;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "lote_id")
    private Lote lote;

    public static Entrega criarEntrega(Endereco endereco) {
        return Entrega.builder()
                       .endereco(endereco)
                       .status(SituacaoEnum.CRIADO.getValor())
                       .codigoRastreio(criarCodigoRastreio())
                       .build();
    }

    public static EntregaResponseDto toEntregaResponseDto(Entrega entrega) {
        return EntregaResponseDto.builder()
                       .id(entrega.id)
                       .enderecoEntregaDto(Endereco.toDto(entrega.endereco))
                       .situacao(SituacaoEnum.getSituacao(entrega.status))
                       .codigoRastreio(entrega.codigoRastreio)
                       .dataHoraEntrega(converterDataHora(entrega.dataHoraEntrega))
                       .entregador(entrega.entregador)
                       .loteId(entrega.lote.getId())
                       .localizacaoDto(criarLocalizacaoDto(entrega.longitude, entrega.latitude))
                       .build();
    }

    public static EntregaLoteResponseDto toEntregaLoteResponseDto(Entrega entrega) {
        return EntregaLoteResponseDto.builder()
                       .id(entrega.id)
                       .enderecoEntregaDto(Endereco.toDto(entrega.endereco))
                       .situacao(SituacaoEnum.getSituacao(entrega.status))
                       .codigoRastreio(entrega.codigoRastreio)
                       .dataHoraEntrega(converterDataHora(entrega.dataHoraEntrega))
                       .entregador(entrega.entregador)
                       .localizacaoDto(criarLocalizacaoDto(entrega.longitude, entrega.latitude))
                       .build();
    }

    public static Entrega atualizarSituacao(Entrega entrega, String situacao) {
        validarAlteracaoSituacao(entrega, situacao);
        entrega.status = situacao;
        if (validarStatusIgual(SituacaoEnum.getSituacao(situacao), SituacaoEnum.ENTREGUE)) {
            entrega.dataHoraEntrega = criarDataHoraEntrega(situacao);
        }
        return entrega;
    }

    public static Entrega alterarLocalizacao(Entrega entrega, LocalizacaoDto localizacaoDto) {
        entrega.setLatitude(localizacaoDto.latitude());
        entrega.setLongitude(localizacaoDto.longitude());
        return entrega;
    }


    public static void atribuirEntregador(Entrega entrega, String entregador) {
        validarAtribuirEntregador(entrega);
        entrega.entregador = entregador;
    }

    private static String criarCodigoRastreio() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String dataHora = LocalDateTime.now().format(dtf);
        String codigoRandom = Long.toUnsignedString(UUID.randomUUID().getMostSignificantBits());
        String codigo = String.format("ENT%s%s", dataHora, codigoRandom);
        return codigo;
    }

    private static LocalizacaoDto criarLocalizacaoDto(String longitude, String latitude) {
        return new LocalizacaoDto(longitude, latitude);
    }

    private static String converterDataHora(Timestamp dataHora) {
        if(Objects.isNull(dataHora)) {
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm");
        LocalDateTime dataHoraConvertida = dataHora.toLocalDateTime();
        return dataHoraConvertida.format(formatter);
    }

    private static void validarAlteracaoSituacao(Entrega entrega, String situacao) {
        SituacaoEnum situacaoEnum = SituacaoEnum.getSituacao(situacao);
        validarStatusFinal(entrega);
        validarStatusAlteradoSejaProximo(SituacaoEnum.getSituacao(entrega.status), situacaoEnum);
        validarEntregadorAtribuido(entrega, situacaoEnum);
    }

    private static void validarStatusFinal(Entrega entrega) {
        if (validarStatusIgual(SituacaoEnum.getSituacao(entrega.status), SituacaoEnum.ENTREGUE)) {
            throw new ControllerNotFoundException("Não é possível alterar a situação da entrega, pois a entrega foi concluída");
        }
    }

    private static boolean validarStatusIgual(SituacaoEnum situacaoComparada, SituacaoEnum situacao) {
        return situacao.getValor().equals(situacaoComparada.getValor());
    }

    private static void validarStatusAlteradoSejaProximo(SituacaoEnum situacaoGravada, SituacaoEnum situacaoEnum) {
        if (situacaoGravada.ordinal() == 0 || situacaoEnum.ordinal() == 0) {
            return;
        }

        if (situacaoGravada.ordinal() != (situacaoEnum.ordinal() - 1)) {
            throw new ControllerNotFoundException(
                    String.format("A situação: %s não corresponde a próxima etapa", situacaoEnum.getValor()));
        }
    }

    private static void validarEntregadorAtribuido(Entrega entrega, SituacaoEnum situacao) {
        if(situacao.ordinal() >= 5 && Objects.isNull(entrega.getEntregador())) {
            throw new ControllerNotFoundException(
                    String.format("Não é permitido avançar situação para %s sem entregador atribuído", situacao.getValor()));
        }
    }

    private static Timestamp criarDataHoraEntrega(String situacao) {
        String dataHora = LocalDateTime.now()
                                  .truncatedTo(ChronoUnit.SECONDS)
                                  .format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));
        return Timestamp.valueOf(dataHora);
    }

    private static void validarAtribuirEntregador(Entrega entrega) {
        if (SituacaoEnum.getSituacao(entrega.status).ordinal() >= 5) {
            throw new ControllerNotFoundException(
                    String.format("Não é permitido alterar entregador com a entrega %s ou %s",
                            SituacaoEnum.ENTREGUE.getValor(), SituacaoEnum.EM_ROTA_ENTREGA.getValor()));
        }

        if(Objects.nonNull(entrega.entregador)) {
            throw new ControllerNotFoundException(
                    String.format("Já existe entregador atribuído a entrega: %s", entrega.entregador));
        }
    }
}
