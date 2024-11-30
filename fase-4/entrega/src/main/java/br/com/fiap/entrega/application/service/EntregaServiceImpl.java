package br.com.fiap.entrega.application.service;

import br.com.fiap.entrega.application.dto.*;
import br.com.fiap.entrega.domain.entity.Endereco;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.entity.Lote;
import br.com.fiap.entrega.domain.repository.EntregaRepository;
import br.com.fiap.entrega.domain.service.EnderecoService;
import br.com.fiap.entrega.domain.service.EntregaService;
import br.com.fiap.entrega.domain.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EntregaServiceImpl implements EntregaService {

    private final EnderecoService enderecoService;
    private final LoteService loteService;
    private final EntregaRepository repository;

    @Override
    public EntregaResponseDto criarEntrega(UUID enderecoUsuarioId) {
        Endereco endereco = enderecoService.buscarEnderecoPorId(enderecoUsuarioId);
        Optional<Lote> lote = loteService.buscarLotePorCep(endereco.getCep());
        Entrega entrega = Entrega.criarEntrega(endereco);
        lote.ifPresentOrElse(entrega::setLote,
                () -> entrega.setLote(Lote.criarLote(endereco.getCep())));

        return Entrega.toEntregaResponseDto(repository.persistir(entrega));
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

    private Entrega buscarEntregaPorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
