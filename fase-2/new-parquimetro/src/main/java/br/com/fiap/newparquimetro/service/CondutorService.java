package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.dto.condutor.CondutorResponseDTO;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CondutorService {

    private CondutorRepository condutorRepository;
    private FormaPagamentoService formaPagamentoService;

    public CondutorResponseDTO save(Condutor condutor) {
        Condutor condutorSalvo = this.condutorRepository.save(condutor);

        if (condutorSalvo.getId() == null) {
            throw new ControllerNotFoundException("Ocorreu um erro ao salvar novo condutor");
        }

        return CondutorResponseDTO.toDTO(condutorSalvo, this.formaPagamentoService.findById(condutor.getIdFormaPagamento()));
    }

    public CondutorResponseDTO update(Condutor condutor) {
        Condutor condutorEncontrado = this.condutorRepository.findById(condutor.getId()).orElseThrow(() -> new ControllerNotFoundException("Usuario não encontrado"));
        condutorEncontrado.setNome(condutor.getNome() != null ? condutor.getNome() : condutorEncontrado.getNome());
        condutorEncontrado.setCpfCnpj(condutor.getCpfCnpj() != null ? condutor.getCpfCnpj() : condutorEncontrado.getCpfCnpj());
        condutorEncontrado.setDataNascimento(condutor.getDataNascimento() != null ? condutor.getDataNascimento() : condutorEncontrado.getDataNascimento());
        condutorEncontrado.setTelefone(condutor.getTelefone() != null ? condutor.getTelefone() : condutorEncontrado.getTelefone());
        condutorEncontrado.setIdFormaPagamento(condutor.getIdFormaPagamento() != null ? condutor.getIdFormaPagamento() : condutorEncontrado.getIdFormaPagamento());
        condutorEncontrado.setEndereco(condutor.getEndereco() != null ? condutor.getEndereco() : condutorEncontrado.getEndereco());
        condutorEncontrado.setVeiculos(condutor.getVeiculos() != null ? condutor.getVeiculos() : condutorEncontrado.getVeiculos());

        return CondutorResponseDTO.toDTO(this.condutorRepository.save(condutorEncontrado), this.formaPagamentoService.findById(condutorEncontrado.getIdFormaPagamento()));
    }

    public void delete(Condutor condutor) {
        Condutor condutorEncontrado = this.condutorRepository.findById(condutor.getId()).orElseThrow(() -> new ControllerNotFoundException("Usuario não encontrado"));
        this.condutorRepository.delete(condutorEncontrado);
    }

    public CondutorResponseDTO find(String id) {
        Condutor condutor = this.condutorRepository.findById(id).orElseThrow(() -> new ControllerNotFoundException("Usuario não encontrado"));
        return CondutorResponseDTO.toDTO(condutor, this.formaPagamentoService.findById(condutor.getIdFormaPagamento()));
    }
}
