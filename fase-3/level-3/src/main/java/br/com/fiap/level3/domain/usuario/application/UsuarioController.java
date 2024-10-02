package br.com.fiap.level3.domain.usuario.application;

import br.com.fiap.level3.domain.usuario.core.model.usuario.UsuarioDTO;
import br.com.fiap.level3.domain.usuario.core.ports.incoming.FindUsuario;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final FindUsuario findUsuario;

    @GetMapping("")
    public ResponseEntity<List<UsuarioDTO>> listaTodosTipoRestaurante() {
        var usuarios = this.findUsuario.getUsuarios();

        if (usuarios.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(UsuarioDTO.fromUsuario(usuarios));
    }

}
