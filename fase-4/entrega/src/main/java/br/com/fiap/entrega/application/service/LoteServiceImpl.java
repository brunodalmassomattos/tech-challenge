package br.com.fiap.entrega.application.service;

import br.com.fiap.entrega.application.dto.LocalizacaoDto;
import br.com.fiap.entrega.application.dto.LoteEntregaResponseDto;
import br.com.fiap.entrega.application.enumerator.SituacaoEnum;
import br.com.fiap.entrega.domain.entity.Entrega;
import br.com.fiap.entrega.domain.entity.Lote;
import br.com.fiap.entrega.domain.repository.LoteRepository;
import br.com.fiap.entrega.domain.service.LoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoteServiceImpl implements LoteService {

    private final LoteRepository repository;

    @Override
    public Optional<Lote> buscarLotePorCep(String cep) {
        String cepSubSetor = cep.substring(0, 5);
        return repository.buscarPorCep(cepSubSetor, SituacaoEnum.CRIADO.getValor());
    }

    @Override
    public LoteEntregaResponseDto atualizarLocalizacao(UUID id, LocalizacaoDto localizacaoDto) {
        Lote lote = buscarLotePorId(id);
        Lote.atualizarLocalizacao(lote, localizacaoDto);
        lote.getEntregas()
                .forEach(entrega -> Entrega.alterarLocalizacao(entrega, localizacaoDto));
        return Lote.toLoteEntregaResponseDto(repository.persistir(lote));
    }

    @Override
    public LoteEntregaResponseDto atribuirTransportadora(UUID id, String trasportadora) {
        Lote lote = buscarLotePorId(id);
        Lote.atribuirTransportadora(lote, trasportadora);
        return Lote.toLoteEntregaResponseDto(repository.persistir(lote));
    }

    @Override
    public LoteEntregaResponseDto atualizarSituacao(UUID id, String situacao) {
        Lote lote = buscarLotePorId(id);
        Lote.atualizarSituacao(lote, situacao);
        lote.getEntregas()
                .forEach(entrega -> Entrega.atualizarSituacao(entrega, situacao));
        return Lote.toLoteEntregaResponseDto(repository.persistir(lote));
    }

    @Override
    public LoteEntregaResponseDto buscarEntregas(UUID id) {
        Lote lote = buscarLotePorId(id);
        return Lote.toLoteEntregaResponseDto(lote);
    }

    private Lote buscarLotePorId(UUID id) {
        return repository.buscarPorId(id);
    }
}
