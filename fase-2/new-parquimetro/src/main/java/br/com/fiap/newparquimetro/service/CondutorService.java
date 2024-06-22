package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.dto.CondutorResponseDTO;
import br.com.fiap.newparquimetro.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CondutorService {

    private UsuarioRepository usuarioRepository;

    public CondutorResponseDTO save(Condutor usuario) {
        Condutor save = this.usuarioRepository.save(usuario);
        return null;
    }

}
