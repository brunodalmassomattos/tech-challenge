package br.com.fiap.newparquimetro.domain.condutor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "condutores", uniqueConstraints = {@UniqueConstraint(columnNames = "CPF_CNPJ")})

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    @Column(name = "CPF_CNPJ", unique = true)
    private String cpfCnpj;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private String telefone;

    private String idFormaPagamento;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @JoinColumn(name = "condutor_id")
    private List<Veiculo> veiculos;
}
