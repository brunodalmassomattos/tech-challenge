package br.com.fiap.mslogistica.application.service;

import br.com.fiap.mslogistica.domain.entity.Endereco;
import br.com.fiap.mslogistica.domain.repository.EnderecoRepository;
import br.com.fiap.mslogistica.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository repository;

    @Override
    public Endereco buscarEnderecoPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
