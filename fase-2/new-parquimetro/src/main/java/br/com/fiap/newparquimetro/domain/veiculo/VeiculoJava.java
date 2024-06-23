package br.com.fiap.newparquimetro.domain.veiculo;


import br.com.fiap.newparquimetro.dto.AtualizaVeiculoDTO;
import br.com.fiap.newparquimetro.dto.CadastraVeiculoDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class VeiculoJava {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fabricante;
    private String modelo;
    private String placa;
    private String cor;
    private String renavam;
    private int ano;

    public VeiculoJava(CadastraVeiculoDTO dado) {
        this.fabricante = dado.fabricante();
        this.modelo = dado.modelo();
        this.placa = dado.placa();
        this.cor = dado.cor();
        this.renavam = dado.renavam();
        this.ano = dado.ano();
    }

    public void atualizarInformacoes(AtualizaVeiculoDTO dado) {
        if(dado.fabricante() != null){
            this.fabricante = dado.fabricante();
        }
        if(dado.modelo() != null){
            this.modelo = dado.modelo();
        }
        if(dado.placa() != null){
            this.placa = dado.placa();
        }
        if(dado.cor() != null){
            this.cor = dado.cor();
        }
        if(dado.renavam() != null){
            this.renavam = dado.renavam();
        }
        if(dado.ano() != 0){
            this.ano = dado.ano();
        }
    }
}
