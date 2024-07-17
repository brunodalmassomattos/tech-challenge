package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerExceptionHandler;
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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private CondutorRepository condutorRepository;

    @Transactional
    public List<VeiculoJava> cadastrarVeiculos(List<CadastraVeiculoDTO> dados, String idCondutor) {
        if (!condutorRepository.existsById(idCondutor)) {
            throw new ControllerNotFoundException("Condutor não encontrado");
        }

        for (CadastraVeiculoDTO dto : dados) {
            if (veiculoRepository.existsByPlaca(dto.placa())) {
                throw new ControllerNotFoundException("Placa já cadastrada: " + dto.placa());
            }
        }

        List<VeiculoJava> veiculos = dados.stream()
                .map(dto -> {
                    VeiculoJava veiculo = new VeiculoJava();
                    veiculo.setModelo(dto.modelo());
                    veiculo.setPlaca(dto.placa());
                    veiculo.setFabricante(dto.fabricante());
                    veiculo.setCor(dto.cor());
                    veiculo.setAno(dto.ano());
                    return veiculo;
                })
                .collect(Collectors.toList());
        return veiculoRepository.saveAll(veiculos);
    }
    public Page<VeiculoJava> listar(Pageable paginacao) {
        return veiculoRepository.findAll(paginacao);
    }

    public Optional<VeiculoJava> buscarPorId(String id) {
        return Optional.ofNullable(veiculoRepository.findById(id)
                .orElseThrow(() -> new ControllerNotFoundException("Veículo não encontrado para o ID: " + id)));
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
