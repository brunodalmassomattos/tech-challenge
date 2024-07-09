package br.com.fiap.newparquimetro.domain.condutor;

import java.time.LocalTime;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tempo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tempo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Temporal(TemporalType.DATE)
    private Date data;
	private LocalTime hrInicio;
	private LocalTime hrFim;
	private String idCondutor;
	private String status;
	
}
