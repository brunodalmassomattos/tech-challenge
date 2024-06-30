package br.com.fiap.wework.service;

import br.com.fiap.wework.controller.exception.ControllerNotFoundException;
import br.com.fiap.wework.domain.usuario.Usuario;
import br.com.fiap.wework.dto.UsuarioResponseDTO;
import br.com.fiap.wework.repositories.UsuarioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UsuaioService {

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
}
