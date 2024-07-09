package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.dto.TarifaRequestDTO;
import br.com.fiap.newparquimetro.dto.TarifaResponseDTO;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    public TarifaResponseDTO save(TarifaRequestDTO tarifaDto) {
        Tarifa tarifa = tarifaRepository.save(TarifaRequestDTO.toEntity(tarifaDto));
        return TarifaResponseDTO.toDto(tarifa);
    }

    public TarifaResponseDTO update(String tarifaId, TarifaRequestDTO tarifaDto) {
        Tarifa tarifa = findById(tarifaId);
        tarifa.setValor(Objects.nonNull(tarifaDto.valor()) ? tarifaDto.valor() : tarifa.getValor());
        tarifa.setTipo(TipoTarifaEnum.getByDescricao(tarifaDto.tipo()).orElse(tarifa.getTipo()));
        tarifaRepository.save(tarifa);
        return TarifaResponseDTO.toDto(tarifa);
    }

    public TarifaResponseDTO get(String id) {
        return TarifaResponseDTO.toDto(findById(id));
    }

    public List<TarifaResponseDTO> getAll() {
        List<Tarifa> tarifas = tarifaRepository.findAll();
        return tarifas.stream().map(TarifaResponseDTO::toDto).toList();
    }

    public void delete(String id) {
        tarifaRepository.deleteById(id);
    }

    private Tarifa findById(String id) {
        return tarifaRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Tarifa não encontrada"));
    }
}
