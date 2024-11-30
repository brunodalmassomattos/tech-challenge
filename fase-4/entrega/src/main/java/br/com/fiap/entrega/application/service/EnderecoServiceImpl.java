package br.com.fiap.entrega.application.service;

import br.com.fiap.entrega.domain.entity.Endereco;
import br.com.fiap.entrega.domain.repository.EnderecoRepository;
import br.com.fiap.entrega.domain.service.EnderecoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

    private final EnderecoRepository repository;

    @Override
    public Endereco buscarEnderecoPorId(UUID id) {
        var teste = repository.buscarPorId(id);
        return teste;

    }
}
