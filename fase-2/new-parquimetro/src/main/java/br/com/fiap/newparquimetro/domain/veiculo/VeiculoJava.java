package br.com.fiap.newparquimetro.domain.veiculo;


import br.com.fiap.newparquimetro.dto.veiculos.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.veiculos.CadastraVeiculoDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "veiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VeiculoJava {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String fabricante;
    private String modelo;
    private String placa;
    private String cor;
    private String ano;

    public VeiculoJava(CadastraVeiculoDTO dado) {
        this.fabricante = dado.fabricante();
        this.modelo = dado.modelo();
        this.placa = dado.placa();
        this.cor = dado.cor();
        this.ano = dado.ano();
    }

    public void atualizarInformacoes(AtualizaVeiculoDTO dado) {
        dado.fabricante().ifPresent(f -> {
            if (!f.isEmpty()) {
                this.fabricante = f;
            }
        });
        dado.modelo().ifPresent(m -> {
            if (!m.isEmpty()) {
                this.modelo = m;
            }
        });
        dado.placa().ifPresent(p -> {
            if (!p.isEmpty()) {
                this.placa = p;
            }
        });
        dado.cor().ifPresent(c -> {
            if (!c.isEmpty()) {
                this.cor = c;
            }
        });
        dado.ano().ifPresent(a -> {
            if (!a.isEmpty()) {
                this.ano = a;
            }
        });
    }
}
