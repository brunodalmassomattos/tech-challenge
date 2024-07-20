package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.controletempo.ControleTempoResponseDTO;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import br.com.fiap.newparquimetro.repositories.PagamentoRepository;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ControleTempoService {

    private final ControleTempoRepository controleTempoRepository;
    private final CondutorRepository condutorRepository;
    private final TarifaRepository tarifaRepository;
    private final PagamentoRepository pagamentoRepository;

    public List<ControleTempoResponseDTO> buscaTempo(String idCondutor, String status) {
        return ControleTempoResponseDTO.toDtoList(this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor, status));
    }

    public ControleTempoResponseDTO save(String idCondutor, Long tempoContratado, String tipo) {
        if (!TipoTarifaEnum.getByDescricao(tipo).isPresent()) {
            throw new IllegalArgumentException("Tipo de tempo inválido");
        }

        if (tempoContratado == null && TipoTarifaEnum.FIXO.name().equals(tipo)) {
            throw new IllegalArgumentException("Tempo vazio para tipo FIXO");
        }

        var condutor = this.condutorRepository.findById(idCondutor).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrado"));
        var tempoExistente = this.controleTempoRepository.findTempoAtivoCondutorStatus(condutor.getId(), "ABERTO");

        if (!tempoExistente.isEmpty()) {
            throw new IllegalArgumentException("Existe tempo em aberto para esse condutor");
        }

        Tempo tempo = new Tempo();
        tempo.setData(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        tempo.setHrInicio(LocalTime.now());
        tempo.setHrFim(LocalTime.now().plusMinutes(TipoTarifaEnum.FIXO.name().equals(tipo) ? tempoContratado : 60));
        tempo.setIdCondutor(condutor.getId());
        tempo.setStatus("ABERTO");
        tempo.setIdTarifa(tarifaRepository.findIdTipoTarifa(TipoTarifaEnum.getByDescricao(tipo)));

        return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));
    }

    public ControleTempoResponseDTO update(String idCondutor, Long tempoContratado) {
        this.condutorRepository.findById(idCondutor).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrado"));

        Tempo tempo = this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor, "ABERTO").get(0);
        if (this.tarifaRepository.findTipoById(tempo.getIdTarifa()).equals("VARIAVEL")) {
            throw new IllegalArgumentException("Tempo em aberto variavel");
        }

        tempo.setHrFim(tempo.getHrFim().plusMinutes(tempoContratado));
        return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));
    }

    public ControleTempoResponseDTO fechaTempo(String idTempo) {
        Tempo tempo = this.controleTempoRepository.findById(idTempo).orElseThrow(() -> new ControllerNotFoundException("Tempo não encontrado"));

        if (tempo.getStatus().equals("FECHADO")) {
            throw new IllegalArgumentException("O tempo ja se encontra fechado");
        }

        tempo.setStatus("FECHADO");

        LocalDate dataIni = LocalDate.now();
        LocalDate dataFim = dataIni.plusDays(1);

        this.controleTempoRepository.save(tempo);

        Tarifa tarifa = this.tarifaRepository.findById(tempo.getIdTarifa()).orElseThrow(() -> new ControllerNotFoundException("Tarifa não encontrada"));

        Condutor condutor = this.condutorRepository.findById(tempo.getIdCondutor()).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrada"));

        Duration duracao = Duration.between(tempo.getHrInicio(), tempo.getHrFim());

        long minutosTrabalhadas = duracao.toMinutes();

        BigDecimal valor = BigDecimal.valueOf(minutosTrabalhadas).multiply(BigDecimal.valueOf(tarifa.getValor() / 60));

        this.pagamentoRepository.save(OpcoesDePagamento.builder()
                .status("PENDENTE")
                .valor(valor)
                .idTempo(tempo.getId())
                .condutor(condutor)
                .build());

        return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));
    }

    public Optional<Tempo> findByID(String idTempo) {
        return this.controleTempoRepository.findById(idTempo);
    }
}
