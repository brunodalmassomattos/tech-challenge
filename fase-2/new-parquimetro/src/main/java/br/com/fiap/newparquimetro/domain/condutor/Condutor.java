package br.com.fiap.newparquimetro.domain.condutor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "condutores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Condutor {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    @Column(name = "CPF_CNPJ")
    private String cpfCnpj;

    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private String telefone;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @OneToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "formaPagamento_id")
    private FormaPagamento formaPagamento;

    @OneToMany(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "condutor_id")
    private List<Veiculo> veiculos;
}
