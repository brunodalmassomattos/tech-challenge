package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.TarifaDTO;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    public TarifaDTO save(TarifaDTO tarifaDTO) {
        Tarifa tarifaRegistrada = tarifaRepository.save(toEntity(tarifaDTO));
        return toDto(tarifaRegistrada);
    }

    public Optional<Tarifa> findById(UUID id) {
        return tarifaRepository.findById(id);
    }

    public TarifaDTO toDto(Tarifa tarifa) {
        return TarifaDTO.builder()
                .tipo(tarifa.getTipo())
                .valor(tarifa.getValor())
                .build();
    }

    public Tarifa toEntity(TarifaDTO tarifaDTO) {
        return Tarifa.builder()
                .tipo(tarifaDTO.tipo())
                .valor(tarifaDTO.valor())
                .build();
    }
}
