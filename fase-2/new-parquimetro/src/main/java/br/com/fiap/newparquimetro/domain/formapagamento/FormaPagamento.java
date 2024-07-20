package br.com.fiap.newparquimetro.domain.formapagamento;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "formasPagamento")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String tipo;
}
