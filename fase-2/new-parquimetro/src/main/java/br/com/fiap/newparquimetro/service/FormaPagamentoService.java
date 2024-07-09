package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.formapagamento.FormaPagamento;
import br.com.fiap.newparquimetro.dto.FormaPagamentoResponseDTO;
import br.com.fiap.newparquimetro.repositories.FormaPagamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FormaPagamentoService {

    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamento findById(String id) {
        return this.formaPagamentoRepository.findById(id).orElse(null);
    }

    public FormaPagamentoResponseDTO save(FormaPagamento formaPagamento) {
        return FormaPagamentoResponseDTO.toDTO(this.formaPagamentoRepository.save(formaPagamento));
    }

    public FormaPagamentoResponseDTO update(FormaPagamento formaPagamento) {
        FormaPagamento formaPagamentoEncontrado = this.formaPagamentoRepository.findById(formaPagamento.getId()).orElse(null);
        formaPagamentoEncontrado.setTipo(formaPagamento.getTipo() != null ? formaPagamento.getTipo() : formaPagamentoEncontrado.getTipo());

        return FormaPagamentoResponseDTO.toDTO(this.formaPagamentoRepository.save(formaPagamentoEncontrado));
    }

    public void delete(String id) {
        this.formaPagamentoRepository.delete(FormaPagamento.builder().id(id).build());
    }

    public List<FormaPagamento> getAllFormasPagamentos() {
        return this.formaPagamentoRepository.findAll();
    }
}
