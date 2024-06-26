package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.AtualizarOpcaoPagamentoDTO;
import br.com.fiap.newparquimetro.dto.CriarOpcaoPagamentoDTO;
import br.com.fiap.newparquimetro.dto.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpcoesDePagamentoService {

    @Autowired
    private OpcoesDePagamentoRepository opcoesDePagamentoRepository;

    public OpcoesDePagamento findById(String id) {
        return this.opcoesDePagamentoRepository.findById(id).orElse(null);
    }
    @Transactional
    public OpcoesDePagamentoDTO save(CriarOpcaoPagamentoDTO criarOpcaoPagamentoDTO) {
        OpcoesDePagamento opcao = new OpcoesDePagamento(criarOpcaoPagamentoDTO);
        return OpcoesDePagamentoDTO.toDTO(this.opcoesDePagamentoRepository.save(opcao));
    }
    @Transactional
    public OpcoesDePagamentoDTO update(String id, AtualizarOpcaoPagamentoDTO atualizarOpcaoPagamentoDTO) {
        OpcoesDePagamento opcaoExistente = this.opcoesDePagamentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Opção de pagamento não encontrada"));

        opcaoExistente.atualizarDados(atualizarOpcaoPagamentoDTO);
        return OpcoesDePagamentoDTO.toDTO(this.opcoesDePagamentoRepository.save(opcaoExistente));
    }
    @Transactional
    public void delete(String id) {
        this.opcoesDePagamentoRepository.deleteById(id);
    }
}
