package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Recibo;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.ReciboDTO;
import br.com.fiap.newparquimetro.dto.ReciboRequestDTO;
import br.com.fiap.newparquimetro.repositories.ReciboRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReciboService {

    @Autowired
    private ReciboRepository reciboRepository;

    @Autowired
    private TarifaService tarifaService;

    @Autowired
    private CondutorService condutorService;

    public ReciboDTO emitirRecibo(String condutorId) {
        Optional<Recibo> recibo = reciboRepository.findByCondutorId(condutorId);
        ReciboDTO.ReciboDTOBuilder reciboDTOBuilder = ReciboDTO.builder();

        recibo.ifPresent(reciboEncontrado -> toDto(reciboEncontrado, reciboDTOBuilder));

        return reciboDTOBuilder.build();
    }

    public ReciboDTO save(ReciboRequestDTO reciboDTO) {
        Recibo recibo = toEntity(reciboDTO);
        ReciboDTO.ReciboDTOBuilder reciboDTORegistrado = ReciboDTO.builder();

        toDto(reciboRepository.save(recibo), reciboDTORegistrado);

        return reciboDTORegistrado.build();
    }

    private void toDto(Recibo recibo, ReciboDTO.ReciboDTOBuilder reciboDTOBuilder) {
        reciboDTOBuilder
                .tempo(recibo.getTempo().toLocalTime())
                .cpfCnpjCondutor(recibo.getCondutor().getCpfCnpj())
                .nomeCondutor(recibo.getCondutor().getNome())
                .valorTotal(recibo.getValorTotal())
                .tarifa(tarifaService.toDto(recibo.getTarifa()));
    }

    private Recibo toEntity(ReciboRequestDTO reciboDTO) {
        return Recibo.builder()
                .tempo(getTempoFormatado(reciboDTO.tempo()))
                .valorTotal(reciboDTO.valorTotal())
                .condutor(getCondutorById(reciboDTO.condutorId()))
                .tarifa(getTarifaById(reciboDTO.tarifa()))
                .build();
    }

    private Time getTempoFormatado(String tempo) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return Time.valueOf(LocalTime.parse(tempo, formatter));
    }

    private Condutor getCondutorById(String condutorId) {
        return condutorService.findById(condutorId).orElse(null);
    }

    private Tarifa getTarifaById(UUID tarifaId) {
        return tarifaService.findById(tarifaId).orElse(null);
    }
}
