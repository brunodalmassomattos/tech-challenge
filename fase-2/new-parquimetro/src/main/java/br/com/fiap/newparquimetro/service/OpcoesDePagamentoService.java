package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.formapagamento.FormaPagamentoResponseDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoListDTO;
import br.com.fiap.newparquimetro.dto.recibos.ReciboResponseDTO;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OpcoesDePagamentoService {

    private OpcoesDePagamentoRepository repository;
    private CondutorService condutorService;
    private ReciboService reciboService;
    private TarifaRepository tarifaRepository;

    @Autowired
    public OpcoesDePagamentoService(OpcoesDePagamentoRepository repository, CondutorService condutorService, @Lazy ReciboService reciboService, TarifaRepository tarifaRepository) {
        this.repository = repository;
        this.condutorService = condutorService;
        this.reciboService = reciboService;
        this.tarifaRepository = tarifaRepository;
    }

    public OpcoesDePagamento findById(String id) {
        return this.repository.findById(id).orElse(null);
    }

    @Transactional
    public OpcoesDePagamentoDTO save(OpcoesDePagamento opcao) {
        var pagamentoSalvo = this.repository.save(opcao);
        var condutor = condutorService.find(opcao.getCondutor().getId());
        var pagamentoDTO = OpcoesDePagamentoDTO.toDTO(pagamentoSalvo);
        pagamentoDTO.setCondutor(condutor);
        return pagamentoDTO;
    }

    public List<OpcoesDePagamentoDTO> getAll() {
        List<OpcoesDePagamento> pagamentos = repository.findAll();
        return pagamentos.stream().map(OpcoesDePagamentoDTO::toDTO).toList();
    }

    public List<OpcoesDePagamentoListDTO> findPendentesByCondutorId(String condutorId) {
        List<OpcoesDePagamento> pagamentos = repository.findByCondutorIdAndStatus(condutorId, "Pendente");
        return pagamentos.stream()
                .map(opcao -> new OpcoesDePagamentoListDTO(opcao.getId(), opcao.getStatus(), opcao.getDataPagamento()))
                .collect(Collectors.toList());
    }
    public OpcoesDePagamentoDTO simularPagamento(String id) {
        OpcoesDePagamento opcao = repository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        String tipoTarifa = tarifaRepository.findTipoById(opcao.getIdTempo());
        FormaPagamentoResponseDTO formaPagamento = condutorService.findFormaPagamentoCadastrada(opcao.getCondutor().getId());


        if ("Pix".equals(formaPagamento.tipoDescricao()) && !"FIXO".equals(tipoTarifa)) {
            throw new IllegalArgumentException("Pagamento Pix só disponível para tarifa fixa.");
        }

        opcao.setStatus("Pago");
        repository.save(opcao);

        ReciboResponseDTO recibo = reciboService.gerarRecibo(opcao.getId());

        OpcoesDePagamentoDTO dto = OpcoesDePagamentoDTO.toDTO(opcao);
        dto.setRecibo(recibo);
        return dto;
    }
}