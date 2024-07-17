package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoListDTO;
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

    public List<OpcoesDePagamentoListDTO> findAllByCondutorId(String condutorId) {
        List<OpcoesDePagamento> pagamentos = repository.findAllByCondutorId(condutorId);
        return pagamentos.stream()
                .map(opcao -> new OpcoesDePagamentoListDTO(opcao.getId(), opcao.getStatus(), opcao.getDataPagamento()))
                .collect(Collectors.toList());
    }

    public List<OpcoesDePagamentoDTO> getAll() {
        List<OpcoesDePagamento> pagamentos = repository.findAll();
        return pagamentos.stream().map(OpcoesDePagamentoDTO::toDTO).toList();
    }

    @Transactional
    public OpcoesDePagamentoDTO simularPagamento(String id) {
        OpcoesDePagamento opcao = repository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        opcao.setStatus("Pago");
        return OpcoesDePagamentoDTO.toDTO(repository.save(opcao));
    }
}
