package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.recibos.ReciboResponseDTO;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import br.com.fiap.newparquimetro.repositories.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    private ControleTempoRepository controleTempoRepository;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private FormaPagamentoService formaPagamentoService;

    @Autowired
    private ControleTempoService controleTempoService;

    @Autowired
    private OpcoesDePagamentoService opcoesDePagamentoService;

    public List<ReciboResponseDTO> emitirRecibo(String condutorId, String dataInicial, String dataFinal) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime dataInicioFormatada = LocalDateTime.parse(dataInicial, formatter);
        LocalDateTime dataFimFormatada = LocalDateTime.parse(dataFinal, formatter);
        List<Recibo> recibos = reciboRepository.consultarPorIdCondutorEDataInicioEDataFim(
                condutorId, dataInicioFormatada, dataFimFormatada);

        return recibos.stream().
                       map(recibo -> createReciboDto(recibo, buscarTempo(recibo.getPagamento().getIdTempo())))
                       .toList();
    }

    public ReciboResponseDTO gerarRecibo(String idPagamento) {
        OpcoesDePagamento opcoesDePagamento = opcoesDePagamentoService.findById(idPagamento);
        Tempo tempo = buscarTempo(opcoesDePagamento.getIdTempo());

        Recibo recibo = reciboRepository.save(createRecibo(opcoesDePagamento));

        return createReciboDto(recibo, tempo);
    }

    private Tempo buscarTempo(String idTempo) {
        return controleTempoRepository.findById(idTempo)
                .orElseThrow(() -> new ControllerNotFoundException("Tempo não encontrado"));
    }

    private Recibo createRecibo(OpcoesDePagamento opcoesDePagamento) {
        return Recibo.builder()
                       .pagamento(opcoesDePagamento)
                       .data(LocalDateTime.now())
                       .build();
    }

    private ReciboResponseDTO createReciboDto(Recibo recibo, Tempo tempo) {
        return ReciboResponseDTO.builder()
                       .id(recibo.getId())
                       .tarifa(tarifaService.get(tempo.getIdTarifa()))
                       .nomeCondutor(recibo.getPagamento().getCondutor().getNome())
                       .cpfCnpjCondutor(recibo.getPagamento().getCondutor().getCpfCnpj())
                       .tempo(getTempoFormatado(tempo.getHrInicio(), tempo.getHrFim()))
                       .valorTotal(recibo.getPagamento().getValor())
                       .data(recibo.getData())
                       .build();
    }

    private static LocalTime getTempoFormatado(LocalTime horaInicial, LocalTime horaFinal) {
        Duration minutos = Duration.ofMinutes(horaInicial.until(horaFinal, ChronoUnit.MINUTES));
        long horas = minutos.toHours();
        long minutosRestantes = minutos.minusHours(horas).toMinutes();
        return LocalTime.of((int) horas, (int) minutosRestantes);
    }
}
