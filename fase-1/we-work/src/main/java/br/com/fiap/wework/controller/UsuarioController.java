package br.com.fiap.wework.controller;

import br.com.fiap.wework.domain.usuario.Perfil;
import br.com.fiap.wework.domain.usuario.Usuario;
import br.com.fiap.wework.dto.UsuarioAlteracaoRequestDTO;
import br.com.fiap.wework.dto.UsuarioRequestDTO;
import br.com.fiap.wework.dto.UsuarioResponseDTO;
import br.com.fiap.wework.service.UsuaioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UsuarioController {

    private final UsuaioService userService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> user(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) throws ParseException {
        return ResponseEntity.ok(this.userService.save(
                new Usuario(
                        null,
                        usuarioRequestDTO.nome(),
                        usuarioRequestDTO.cpfCnpj(),
                        UsuarioRequestDTO.parseDate(usuarioRequestDTO.dataNascimento()),
                        UsuarioRequestDTO.parseDate(usuarioRequestDTO.dataAbertura()),
                        usuarioRequestDTO.email(),
                        usuarioRequestDTO.senha(),
                        Perfil.builder().descricao(usuarioRequestDTO.perfil()).build(),
                        "ATIVO")));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable String id, @Valid @RequestBody UsuarioAlteracaoRequestDTO usuarioRequestDTO) throws ParseException {
        return ResponseEntity.ok(this.userService.update(
                new Usuario(id,
                        usuarioRequestDTO.nome(),
                        usuarioRequestDTO.cpfCnpj(),
                        UsuarioRequestDTO.parseDate(usuarioRequestDTO.dataNascimento()),
                        UsuarioRequestDTO.parseDate(usuarioRequestDTO.dataAbertura()),
                        usuarioRequestDTO.email(),
                        usuarioRequestDTO.senha(),
                        Perfil.builder().descricao(usuarioRequestDTO.perfil()).build(),
                        usuarioRequestDTO.status())));
    }

}
