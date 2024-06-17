package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.dto.UsuarioAlteracaoRequestDTO;
import br.com.fiap.newparquimetro.dto.UsuarioRequestDTO;
import br.com.fiap.newparquimetro.dto.UsuarioResponseDTO;
import br.com.fiap.newparquimetro.domain.usuario.Perfil;
import br.com.fiap.newparquimetro.domain.usuario.Usuario;
import br.com.fiap.newparquimetro.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UsuarioController {

    private final UsuarioService userService;

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
