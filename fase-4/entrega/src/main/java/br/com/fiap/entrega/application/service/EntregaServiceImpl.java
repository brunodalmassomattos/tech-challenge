package br.com.fiap.entrega.application.service;

import br.com.fiap.entrega.application.dto.*;
import br.com.fiap.entrega.application.gateway.ClienteGateway;
import br.com.fiap.entrega.application.event.PedidoProducer;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.entity.Lote;
import br.com.fiap.entrega.domain.repository.EntregaRepository;
import br.com.fiap.entrega.domain.service.EnderecoService;
import br.com.fiap.entrega.domain.service.EntregaService;
import br.com.fiap.entrega.domain.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EnderecoService enderecoService;
    private final LoteService loteService;
    private final EntregaRepository repository;
    private final ClienteGateway gateway;
    private final PedidoProducer pedidoProducer;

    @Override
    @Transactional
    public void criarEntrega(PedidoConsumerDto pedidoConsumerDto) {
        ClienteDto clienteDto = gateway.obterClientePorId(pedidoConsumerDto.clienteId());

        Optional<Lote> lote = loteService.buscarLotePorCep(clienteDto.enderecoEntrega().cep());
        Entrega entrega = Entrega.criarEntrega(pedidoConsumerDto.notaFiscal(),
                enderecoService.buscarEnderecoPorId(clienteDto.enderecoEntrega().id()));
        lote.ifPresentOrElse(entrega::setLote,
                () -> entrega.setLote(Lote.criarLote(clienteDto.enderecoEntrega().cep())));

        repository.persistir(entrega);
        enviarCodigoRastreio(entrega);
    }

    @Override
    public EntregaResponseDto atualizarSituacao(UUID id, String situacao) {
        Entrega entrega = buscarEntregaPorId(id);
        Entrega.atualizarSituacao(entrega, situacao);
        return Entrega.toEntregaResponseDto(repository.persistir(entrega));
    }

    @Override
    public EntregaResponseDto atribuirEntregador(UUID id, String entregador) {
        Entrega entrega = buscarEntregaPorId(id);
        Entrega.atribuirEntregador(entrega, entregador);
        return Entrega.toEntregaResponseDto(repository.persistir(entrega));
    }

    @Override
    public EntregaResponseDto buscarEntregaPorCodigoRastreio(String codigoRastreio) {
        Entrega entrega = repository.buscarPorCodigoRastreio(codigoRastreio);
        return Entrega.toEntregaResponseDto(entrega);
    }

    @Override
    public EntregaResponseDto atualizarLocalizacaoEntrega(UUID id, LocalizacaoDto localizacao) {
        Entrega entrega = buscarEntregaPorId(id);
        Entrega.alterarLocalizacao(entrega, localizacao);
        return Entrega.toEntregaResponseDto(repository.persistir(entrega));
    }

    private void enviarCodigoRastreio(Entrega entrega) {
        PedidoProducerDto pedidoProducerDto = new PedidoProducerDto(
                "Rastreio",
                entrega.getNotaFiscal(),
                entrega.getCodigoRastreio());
        pedidoProducer.sendMessage("rastreio", pedidoProducerDto);
    }

    private Entrega buscarEntregaPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
