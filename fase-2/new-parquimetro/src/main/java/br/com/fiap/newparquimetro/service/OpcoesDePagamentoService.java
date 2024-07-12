package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpcoesDePagamentoService {

    @Autowired
    private OpcoesDePagamentoRepository repository;

    @Autowired
    private CondutorService condutorService;

    public OpcoesDePagamento findById(String id) {
        return this.repository.findById(id).orElse(null);
    }
    @Transactional
    public OpcoesDePagamentoDTO save(OpcoesDePagamento opcao) {
//        OpcoesDePagamento save = this.opcoesDePagamentoRepository.save(opcao);
//        save.setCondutor(condutorService.findById());
        return OpcoesDePagamentoDTO.toDTO(this.repository.save(opcao));
    }

    @Transactional(readOnly = true)
    public List<OpcoesDePagamentoDTO> findAllByCondutorId(String condutorId) {
        List<OpcoesDePagamento> pagamentos = repository.findAll();
        return pagamentos.stream()
                .map(OpcoesDePagamentoDTO::toDTO)
                .collect(Collectors.toList());
    }
    public List<OpcoesDePagamentoDTO> getAll() {
        List<OpcoesDePagamento> pagamentos = repository.findAll();
        return pagamentos.stream().map(OpcoesDePagamentoDTO::toDTO).toList();
    }
}
