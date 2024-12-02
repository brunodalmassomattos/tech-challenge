package br.com.fiap.entrega.persistence.repository;

import br.com.fiap.entrega.application.exception.ControllerNotFoundException;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.repository.EntregaRepository;
import br.com.fiap.entrega.persistence.data.DataEntregaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EntregaRepositoryImpl implements EntregaRepository {

    private final DataEntregaRepository dataRepository;

    @Override
    public Entrega buscarPorId(UUID id) {
        return dataRepository.findById(id)
                        .orElseThrow(() -> new ControllerNotFoundException(
                                String.format("Não existe entrega para o id: %s", id.toString())));
    }

    @Override
    public Entrega buscarPorCodigoRastreio(String codigoRastreio) {
        return dataRepository.findByCodigoRastreio(codigoRastreio)
                .orElseThrow(() -> new ControllerNotFoundException(
                        String.format("Não existe entrega para o código: %s", codigoRastreio)));
    }

    @Override
    public Entrega persistir(Entrega entrega) {
        return dataRepository.save(entrega);
    }
}
