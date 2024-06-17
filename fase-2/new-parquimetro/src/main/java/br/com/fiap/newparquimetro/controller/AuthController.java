package br.com.fiap.newparquimetro.controller;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.usuario.Usuario;
import br.com.fiap.newparquimetro.dto.LoginRequestDTO;
import br.com.fiap.newparquimetro.dto.LoginResponseDTO;
import br.com.fiap.newparquimetro.security.TokenService;
import br.com.fiap.newparquimetro.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Usuario user = usuarioService.findByEmail(loginRequestDTO.email()).orElseThrow(() -> new ControllerNotFoundException("User not found"));

        if (!passwordEncoder.matches(loginRequestDTO.password(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getId(), user.getNome(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
