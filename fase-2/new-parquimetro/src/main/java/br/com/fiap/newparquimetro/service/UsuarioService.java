package br.com.fiap.newparquimetro.service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.usuario.Usuario;
import br.com.fiap.newparquimetro.dto.UsuarioResponseDTO;
import br.com.fiap.newparquimetro.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {

    private UsuarioRepository usuarioRepository;
    private PerfilService perfilService;

    public UsuarioResponseDTO save(Usuario usuario) {
        usuario.setPerfil(this.perfilService.findByDescricao(usuario.getPerfil().getDescricao()));
        return UsuarioResponseDTO.toDTO(this.usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO update(Usuario usuario) {
        Usuario usuarioSelecionado = this.usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new ControllerNotFoundException("User not found"));

        usuarioSelecionado.setNome(usuario.getNome());
        usuarioSelecionado.setCpfCnpj(usuario.getCpfCnpj());
        usuarioSelecionado.setDataNascimento(usuario.getDataNascimento() == null ? usuarioSelecionado.getDataNascimento() : usuario.getDataNascimento());
        usuarioSelecionado.setDataAbertura(usuario.getDataAbertura() == null ? usuarioSelecionado.getDataAbertura() : usuario.getDataAbertura());
        usuarioSelecionado.setEmail(usuario.getEmail());
        usuarioSelecionado.setSenha(usuario.getSenha() == null ? usuarioSelecionado.getSenha() : usuario.getSenha());
        usuarioSelecionado.setPerfil(this.perfilService.findByDescricao(usuario.getPerfil().getDescricao()));
        usuarioSelecionado.setStatus(usuario.getStatus() == null ? usuarioSelecionado.getStatus() : usuario.getStatus());

        return UsuarioResponseDTO.toDTO(this.usuarioRepository.save(usuarioSelecionado));
    }

    public Optional<Usuario> findByEmail(String email) {
        return this.usuarioRepository.findByEmail(email);
    }
}
