package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.veiculo.VeiculoJava;
import br.com.fiap.newparquimetro.dto.veiculos.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.veiculos.CadastraVeiculoDTO;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import br.com.fiap.newparquimetro.repositories.VeiculoRepository;
import jakarta.validation.Valid;
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

    @Autowired
    private CondutorRepository condutorRepository;

    @Transactional
    public VeiculoJava cadastrarVeiculo(String idConduto, @Valid CadastraVeiculoDTO dado) {
        if (!condutorRepository.existsById(idConduto)) {
            throw new ControllerNotFoundException("Condutor não encontrado: " + idConduto);
        }

        Optional<VeiculoJava> veiculoExistente = veiculoRepository.findByPlaca(dado.placa());
        if (veiculoExistente.isPresent()) {
            throw new ControllerNotFoundException("Veículo já cadastrado com a placa: " + dado.placa());
        }

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
