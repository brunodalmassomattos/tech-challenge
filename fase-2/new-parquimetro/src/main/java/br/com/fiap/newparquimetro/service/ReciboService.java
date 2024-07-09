package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import br.com.fiap.newparquimetro.dto.*;
import br.com.fiap.newparquimetro.repositories.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private CondutorService condutorService;

    @Autowired
    private ControleTempoService controleTempoService;

    public List<ReciboResponseDTO> emitirRecibo(String condutorId) {
        CondutorResponseDTO condutorDto = condutorService.find(condutorId);
        List<Recibo> recibos = reciboRepository.findByIdCondutor(condutorId);

        return ReciboResponseDTO.toDtoList(recibos, condutorDto);
    }

    public List<ReciboResponseDTO> save(String idCondutor, String idTarifa) {
        CondutorResponseDTO condutorDto = condutorService.find(idCondutor);
        List<ControleTempoResponseDTO> listaControleTempoDto = controleTempoService.buscaTempo(condutorDto.id(), "FECHADO");
        TarifaResponseDTO tarfifaDto = tarifaService.get(idTarifa);

        List<Recibo> recibos = new ArrayList<>();
        listaControleTempoDto.forEach(controleTempoDto -> recibos.add(createRecibo(controleTempoDto, tarfifaDto)));
        reciboRepository.saveAll(recibos);

        return ReciboResponseDTO.toDtoList(recibos, condutorDto);
    }

    private Recibo createRecibo(ControleTempoResponseDTO controleTempoDto,
                                TarifaResponseDTO tarifaDto) {
        Long tempoEstacionado = getTempoEstacionado(controleTempoDto);
        return Recibo.builder()
                       .valorTotal(calcularValorTotal(tempoEstacionado, tarifaDto.valor()))
                       .tempo(tempoEstacionado)
                       .idCondutor(controleTempoDto.idCondutor())
                       .idTempo(controleTempoDto.id())
                       .tarifa(TarifaResponseDTO.toEntity(tarifaDto))
                .build();
    }

    private Long getTempoEstacionado(ControleTempoResponseDTO controleTempoDto) {
        LocalTime horaInicial = controleTempoDto.hrInicio();
        LocalTime horaFinal = controleTempoDto.hrFim();
        return horaInicial.until(horaFinal, ChronoUnit.MINUTES);
    }

    private Double calcularValorTotal(Long tempo, Double valorTarifa) {
        if (tempo <= 60) {
            return valorTarifa;
        }

        long horas = tempo / 60;
        long minutosRestantes = tempo % 60;

        if (minutosRestantes > 30) {
            horas++;
        }

        return horas * valorTarifa;
    }
}
