package br.com.fiap.wework.controller;

import br.com.fiap.wework.controller.exception.ControllerNotFoundException;
import br.com.fiap.wework.domain.usuario.Usuario;
import br.com.fiap.wework.dto.LoginRequestDTO;
import br.com.fiap.wework.dto.LoginResponseDTO;
import br.com.fiap.wework.infra.security.TokenService;
import br.com.fiap.wework.repositories.UsuarioRepository;
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

    private final UsuarioRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO) {
        Usuario user = userRepository.findByEmail(loginRequestDTO.email()).orElseThrow(() -> new ControllerNotFoundException("User not found"));
        if (!passwordEncoder.matches(loginRequestDTO.password(), user.getSenha())) {
            String token = this.tokenService.generateToken(user);
            return ResponseEntity.ok(new LoginResponseDTO(user.getId(), user.getNome(), token));
        }

        return ResponseEntity.badRequest().build();
    }

}
