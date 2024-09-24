package br.com.fiap.level3.domain.avaliacao.core.domain.model.avaliacao;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avaliacao {



	private UUID id;
	private Integer nota;
	private String comentario;
	private UUID restauranteId;
	private UUID usuarioId;
	
	public Avaliacao(UUID id, Integer nota, String comentario) {
		this.id = id;
		this.nota=nota;
		this.comentario=comentario;
	}

}
