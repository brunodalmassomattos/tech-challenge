package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import br.com.fiap.newparquimetro.dto.veiculos.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.veiculos.CadastraVeiculoDTO;
import br.com.fiap.newparquimetro.repositories.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Transactional
    public VeiculoJava cadastrarVeiculo(CadastraVeiculoDTO dado) {
        VeiculoJava veiculo = new VeiculoJava(dado);
        return veiculoRepository.save(veiculo);
    }

    public Page<VeiculoJava> listar(Pageable paginacao) {
        return veiculoRepository.findAll(paginacao);
    }

    public Optional<VeiculoJava> buscarPorId(String id) {
        return veiculoRepository.findById(id);
    }

    @Transactional
    public VeiculoJava atualizar(String id, AtualizaVeiculoDTO dado) {
        VeiculoJava veiculo = veiculoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Veículo não encontrado"));

        veiculo.atualizarInformacoes(dado);

        return veiculoRepository.save(veiculo);
    }

    @Transactional
    public void excluir(String id) {
        veiculoRepository.deleteById(id);
    }
}
