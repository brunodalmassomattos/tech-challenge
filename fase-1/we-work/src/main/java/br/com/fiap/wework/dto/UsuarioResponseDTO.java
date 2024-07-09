package br.com.fiap.wework.dto;

import br.com.fiap.wework.domain.usuario.Perfil;
import br.com.fiap.wework.domain.usuario.Usuario;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;


public record UsuarioResponseDTO(
        String id,
        String nome,
        String cpfCnpj,
        String dataNascimento,
        String dataAbertura,
        String email,
        String senha,
        Perfil perfil,
        String status) implements Serializable {

    public static String parseDate(Date data) {
        if (data == null) {
            return null;
        }

        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public static UsuarioResponseDTO toDTO(Usuario save) {
        return new UsuarioResponseDTO(
                save.getId(),
                save.getNome(),
                save.getCpfCnpj(),
                UsuarioResponseDTO.parseDate(save.getDataNascimento()),
                UsuarioResponseDTO.parseDate(save.getDataAbertura()),
                save.getEmail(),
                save.getSenha(),
                save.getPerfil(),
                save.getStatus());
    }
}
