package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.dto.CondutorResponseDTO;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CondutorService {

    private CondutorRepository condutorRepository;
    private FormaPagamentoService formaPagamentoService;
    private EnderecoService enderecoService;
    private VeiculoService veiculoService;

    public CondutorResponseDTO save(Condutor condutor) {
        Condutor condutorSalvo = this.condutorRepository.save(condutor);

        if (condutorSalvo.getId() == null) {
            throw new ControllerNotFoundException("Ocorreu um erro ao salvar novo condutor");
        }

        return CondutorResponseDTO.toDTO(condutorSalvo, this.formaPagamentoService.findById(condutor.getIdFormaPagamento()));
    }

    public CondutorResponseDTO update(Condutor condutor) {
        Condutor condutorEncontrado = this.condutorRepository.findById(condutor.getId()).orElseThrow(() -> new ControllerNotFoundException("Usuario não encontrado"));

        condutorEncontrado.setNome(condutor.getNome() != null ? condutor.getNome() : null);
        condutorEncontrado.setCpfCnpj(condutor.getCpfCnpj() != null ? condutor.getCpfCnpj() : null);
        condutorEncontrado.setDataNascimento(condutor.getDataNascimento() != null ? condutor.getDataNascimento() : null);
        condutorEncontrado.setTelefone(condutor.getTelefone() != null ? condutor.getTelefone() : null);
        condutorEncontrado.setIdFormaPagamento(condutor.getIdFormaPagamento() != null ? condutor.getIdFormaPagamento() : null);
        condutorEncontrado.setEndereco(condutor.getEndereco() != null ? condutor.getEndereco() : null);
        condutorEncontrado.setVeiculos(condutor.getVeiculos() != null ? condutor.getVeiculos() : null);

        return CondutorResponseDTO.toDTO(this.condutorRepository.save(condutorEncontrado), this.formaPagamentoService.findById(condutor.getIdFormaPagamento()));
    }

    public void delete(Condutor condutor) {
        Condutor condutorEncontrado = this.condutorRepository.findById(condutor.getId()).orElseThrow(() -> new ControllerNotFoundException("Usuario não encontrado"));
        this.condutorRepository.delete(condutorEncontrado);
    }

    public Optional<Condutor> findById(String id) {
        return this.condutorRepository.findById(id);
    }
}
