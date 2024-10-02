package br.com.fiap.level3.domain.usuario.core.model.usuario;

import java.util.List;
import java.util.stream.Collectors;

public record UsuarioDTO(String id, String nome) {
    public static List<UsuarioDTO> fromUsuario(List<Usuario> usuarios) {
        return usuarios
                .stream()
                .map(usuario -> {
                    return new UsuarioDTO(usuario.getId().toString(), usuario.getNome());
                })
                .collect(Collectors.toList());
    }
}
