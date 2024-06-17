package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.domain.usuario.Perfil;
import br.com.fiap.newparquimetro.repositories.PerfilRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PerfilService {

    private PerfilRepository perfilRepository;

    public Perfil findByDescricao(String descricao) {
        return this.perfilRepository.findByDescricao(descricao).orElseThrow(() -> new RuntimeException("User not found"));
    }

}
