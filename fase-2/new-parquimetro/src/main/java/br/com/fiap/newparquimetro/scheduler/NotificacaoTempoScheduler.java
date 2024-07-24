package br.com.fiap.newparquimetro.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class NotificacaoTempoScheduler {

	private final ControleTempoRepository controleTempoRepository;
	private final TarifaRepository tarifaRepository;

	private List<Tempo> tempos;

	@Scheduled(cron = "0 0/1 * * * *", zone = "America/Sao_Paulo")
	public void init() {

		LocalDate dataIni = LocalDate.now();
		LocalDate dataFim = dataIni.plusDays(1);

		LocalTime horaAtual = LocalTime.now();

		this.tempos = this.controleTempoRepository.findTempoAtivoDataStatus(
				Date.from(dataIni.atStartOfDay(ZoneId.systemDefault()).toInstant()),
				Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant()), "ABERTO");

		for (Tempo tempo : tempos) {

			long minutesDifference = ChronoUnit.MINUTES.between(horaAtual, tempo.getHrFim());

			if (this.tarifaRepository.findTipoById(tempo.getIdTarifa()).equals("FIXO")) {

				if (minutesDifference < 10) {

					System.out.println("-- Sua reserva está prestes a expirar em 10 minutos! --");
				}

			} else if (this.tarifaRepository.findTipoById(tempo.getIdTarifa()).equals("VARIAVEL")) {

				if (minutesDifference < 10) {
					System.out.println(
							"-- Sua reserva está prestes a ser renovada em 10 minutos. Para evitar desative o tempo automático! --");
				}

			}
		}

	}

}
