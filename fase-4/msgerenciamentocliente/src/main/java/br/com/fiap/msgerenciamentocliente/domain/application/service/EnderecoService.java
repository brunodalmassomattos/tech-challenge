package br.com.fiap.msgerenciamentocliente.domain.application.service;

import br.com.fiap.msgerenciamentocliente.domain.core.entity.Endereco;
import br.com.fiap.msgerenciamentocliente.domain.core.repository.EnderecoRepository;
import br.com.fiap.msgerenciamentocliente.infraestructure.handler.GlobalExceptionHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class EnderecoService {
    private final EnderecoRepository enderecoRepository;
    private final GlobalExceptionHandler exceptionHandler;

    public EnderecoService(EnderecoRepository enderecoRepository, GlobalExceptionHandler exceptionHandler) {
        this.enderecoRepository = enderecoRepository;
        this.exceptionHandler = exceptionHandler;
    }

    public List<Endereco> listarEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        if (enderecos.isEmpty()) {
            throw new GlobalExceptionHandler.ResourceNotFoundException("Enderecos", "Nenhum registro encontrado");
        }
        return enderecos;
    }

    public Endereco buscarEnderecoPorId(UUID id) {
        exceptionHandler.validateId(id, "Endereco");
        return enderecoRepository.findById(id)
                .orElseThrow(() -> new GlobalExceptionHandler.ResourceNotFoundException("Endereco", id.toString()));
    }

    @Transactional
    public Endereco criarEndereco(Endereco endereco) {
        exceptionHandler.validateEndereco(endereco);
        return enderecoRepository.save(endereco);
    }

    @Transactional
    public Endereco atualizarEndereco(UUID id, Endereco enderecoAtualizado) {
        exceptionHandler.validateId(id, "Endereco");
        exceptionHandler.validateEndereco(enderecoAtualizado);

        // Verifica se o endereço existe
        buscarEnderecoPorId(id);

        enderecoAtualizado.setId(id);
        return enderecoRepository.save(enderecoAtualizado);
    }

    @Transactional
    public void deletarEndereco(UUID id) {
        exceptionHandler.validateId(id, "Endereco");
        buscarEnderecoPorId(id); // Verifica se existe
        enderecoRepository.deleteById(id);
    }

    public Endereco formatarEndereco(Endereco endereco) {
        if (endereco == null) {
            throw new GlobalExceptionHandler.ValidationException("Endereço inválido",
                    List.of("Endereço não pode ser nulo"));
        }

        // Formata o CEP (se necessário)
        if (endereco.getCep() != null) {
            endereco.setCep(formatarCep(endereco.getCep()));
        }

        // Capitaliza o nome da rua
        if (endereco.getRua() != null) {
            endereco.setRua(capitalizarTexto(endereco.getRua()));
        }

        // Capitaliza o nome do bairro
        if (endereco.getBairro() != null) {
            endereco.setBairro(capitalizarTexto(endereco.getBairro()));
        }

        // Capitaliza o nome da cidade
        if (endereco.getCidade() != null) {
            endereco.setCidade(capitalizarTexto(endereco.getCidade()));
        }

        // Converte estado para maiúsculo
        if (endereco.getEstado() != null) {
            endereco.setEstado(endereco.getEstado().toUpperCase());
        }

        return endereco;
    }

    private String formatarCep(String cep) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new GlobalExceptionHandler.ValidationException("CEP inválido",
                    List.of("CEP não pode ser vazio"));
        }

        // Remove caracteres não numéricos
        String cepLimpo = cep.replaceAll("[^0-9]", "");

        if (cepLimpo.length() != 8) {
            throw new GlobalExceptionHandler.ValidationException("CEP inválido",
                    List.of("CEP deve conter 8 dígitos"));
        }

        // Formata no padrão XXXXX-XXX
        return cepLimpo.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
    }

    private String capitalizarTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            return texto;
        }

        String[] palavras = texto.toLowerCase().split(" ");
        StringBuilder resultado = new StringBuilder();

        for (String palavra : palavras) {
            if (!palavra.isEmpty()) {
                if (resultado.length() > 0) {
                    resultado.append(" ");
                }
                resultado.append(Character.toUpperCase(palavra.charAt(0)))
                        .append(palavra.substring(1));
            }
        }

        return resultado.toString();
    }
}