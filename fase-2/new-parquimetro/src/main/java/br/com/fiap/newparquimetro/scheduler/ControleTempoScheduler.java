package br.com.fiap.newparquimetro.scheduler;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class ControleTempoScheduler {

	private final ControleTempoRepository controleTempoRepository;

	private final TarifaRepository tarifaRepository;

	private final CondutorRepository condutorRepository;
	
	private final OpcoesDePagamentoRepository opcoesDePagamentoRepository;

	private List<Tempo> tempos = new ArrayList<>();

	//@Scheduled(cron = "0 0/2 * * * *", zone = "America/Sao_Paulo")
	public void init() {

		LocalDate dataIni = LocalDate.now();
		LocalDate dataFim = dataIni.plusDays(1);

		tempos = this.controleTempoRepository.findTempoAtivoDataStatus(
				Date.from(dataIni.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant()), "ABERTO");

		LocalTime horaAtual = LocalTime.now();

		for (Tempo tempo : tempos) {

			if (this.tarifaRepository.findTipoById(tempo.getIdTarifa()).equals("FIXO")) {

				if (tempo.getHrFim().isBefore(horaAtual)) {

					tempo.setStatus("FECHADO");
					
					this.controleTempoRepository.save(tempo);
				
					Tarifa tarifa = this.tarifaRepository.findById(tempo.getIdTarifa()).orElseThrow(() -> new ControllerNotFoundException("Tarifa não encontrada"));
				
					Condutor condutor = this.condutorRepository.findById(tempo.getIdCondutor()).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrada"));
					
					Duration duracao = Duration.between(tempo.getHrInicio(), tempo.getHrFim());
					
					long minutosTrabalhadas = duracao.toMinutes();
					
					BigDecimal valor = BigDecimal.valueOf(minutosTrabalhadas).multiply(BigDecimal.valueOf(tarifa.getValor()/60));
					
					this.opcoesDePagamentoRepository.save(OpcoesDePagamento.builder()
//							.tipo(this.tarifaRepository.findTipoById(tempo.getIdTarifa()))
							.status("PENDENTE")
							.valor(valor)
							.idTempo(tempo.getId())
							.condutor(condutor)
							.build());

				}

			} else if (this.tarifaRepository.findTipoById(tempo.getIdTarifa()).equals("VARIAVEL")) {

				if(tempo.getHrFim().isBefore(horaAtual)) {
					tempo.setHrFim(tempo.getHrFim().plusMinutes(60));
					this.controleTempoRepository.save(tempo);
				}
				
			}

		}

	}

}
