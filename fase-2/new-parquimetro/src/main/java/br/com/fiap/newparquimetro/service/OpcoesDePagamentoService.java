package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OpcoesDePagamentoService {

    @Autowired
    private OpcoesDePagamentoRepository opcoesDePagamentoRepository;

    @Autowired
    private CondutorService condutorService;

    public OpcoesDePagamento findById(String id) {
        return this.opcoesDePagamentoRepository.findById(id).orElse(null);
    }
    @Transactional
    public OpcoesDePagamentoDTO save(OpcoesDePagamento opcao) {
//        OpcoesDePagamento save = this.opcoesDePagamentoRepository.save(opcao);
//        save.setCondutor(condutorService.findById());
        return OpcoesDePagamentoDTO.toDTO(this.opcoesDePagamentoRepository.save(opcao));
    }
}
