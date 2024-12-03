package br.com.fiap.mslogistica.persistence.repository;

import br.com.fiap.mslogistica.application.exception.ControllerNotFoundException;
import br.com.fiap.mslogistica.domain.entity.Endereco;
import br.com.fiap.mslogistica.domain.repository.EnderecoRepository;
import br.com.fiap.mslogistica.persistence.data.DataEnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class EnderecoRepositoryImpl implements EnderecoRepository {

    private final DataEnderecoRepository repository;

    @Override
    public Endereco buscarPorId(UUID id) {
        return repository.findById(id)
                       .orElseThrow(() -> new ControllerNotFoundException(
                               String.format("Não existe endereço para id: %s", id.toString())));
    }
}
