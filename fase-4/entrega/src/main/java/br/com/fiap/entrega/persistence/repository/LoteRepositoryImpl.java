package br.com.fiap.entrega.persistence.repository;

import br.com.fiap.entrega.application.exception.ControllerNotFoundException;
import br.com.fiap.entrega.domain.entity.Lote;
import br.com.fiap.entrega.domain.repository.LoteRepository;
import br.com.fiap.entrega.persistence.data.DataLoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LoteRepositoryImpl implements LoteRepository {

    private final DataLoteRepository repository;

    @Override
    public Lote buscarPorId(UUID id) {
        return repository.findById(id)
                        .orElseThrow(() -> new ControllerNotFoundException(
                                String.format("Não existe lote de entrega para id: %s", id.toString())));
    }

    @Override
    public Optional<Lote> buscarPorCep(String cep, String situacao) {
        return repository.findByCep(cep, situacao);
    }

    @Override
    public Lote persistir(Lote lote) {
        return repository.save(lote);
    }
}
