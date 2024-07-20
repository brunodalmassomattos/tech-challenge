package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoDTO;
import br.com.fiap.newparquimetro.dto.opcaopagamentos.OpcoesDePagamentoListDTO;
import br.com.fiap.newparquimetro.dto.recibos.ReciboResponseDTO;
import br.com.fiap.newparquimetro.repositories.PagamentoRepository;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService {

    private PagamentoRepository repository;
    private CondutorService condutorService;
    private ReciboService reciboService;
    private TarifaRepository tarifaRepository;
    private ControleTempoService controleTempoService;

    @Autowired
    public PagamentoService(PagamentoRepository repository,
                            CondutorService condutorService,
                            @Lazy ReciboService reciboService,
                            ControleTempoService controleTempoService,
                            TarifaRepository tarifaRepository) {
        this.repository = repository;
        this.condutorService = condutorService;
        this.reciboService = reciboService;
        this.tarifaRepository = tarifaRepository;
        this.controleTempoService = controleTempoService;
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

    public List<OpcoesDePagamentoListDTO> findPendentesByCondutorId(String condutorId) {
        List<OpcoesDePagamento> pagamentos = repository.findByCondutorIdAndStatus(condutorId, "Pendente");
        return pagamentos.stream().map(opcao -> new OpcoesDePagamentoListDTO(opcao.getId(), opcao.getStatus(), opcao.getDataPagamento())).collect(Collectors.toList());
    }

    public OpcoesDePagamentoDTO simularPagamento(String id) {
        var opcao = this.repository.findById(id).orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
        var tempo = this.controleTempoService.findByID(opcao.getIdTempo());
        var tarifa = tarifaRepository.findTipoById(tempo.get().getIdTarifa());
        var formaPagamento = condutorService.findFormaPagamentoCadastrada(opcao.getCondutor().getId());

        if ("PIX".equals(formaPagamento.tipoDescricao()) && !"FIXO".equals(tarifa)) {
            throw new IllegalArgumentException("Pagamento Pix só disponível para tarifa fixa.");
        }

        opcao.setStatus("PAGO");
        opcao.setDataPagamento(
                DateTimeFormatter.ofPattern("yyyy-MM-dd").format(LocalDate.now()));
        repository.save(opcao);

        ReciboResponseDTO recibo = reciboService.gerarRecibo(opcao.getId());

        OpcoesDePagamentoDTO dto = OpcoesDePagamentoDTO.toDTO(opcao);
        dto.setRecibo(recibo);
        return dto;
    }
}