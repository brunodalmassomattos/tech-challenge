package br.com.fiap.newparquimetro.scheduler;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class ControleTempoScheduler {

    private final ControleTempoRepository controleTempoRepository;
	
    private List<Tempo> tempos = new ArrayList<>();

	@Scheduled(cron = "0 0/10 * * * *", zone = "America/Sao_Paulo")
	public void init() {
		
        LocalDate dataIni = LocalDate.now();
        LocalDate dataFim = dataIni.plusDays(1);

		tempos = this.controleTempoRepository.findTempoAtivoDataStatus(Date.from(dataIni.atStartOfDay(ZoneId.systemDefault()).toInstant()),Date.from(dataFim.atStartOfDay(ZoneId.systemDefault()).toInstant()),"ABERTO");
		
        LocalTime horaAtual = LocalTime.now();
		
		for(Tempo tempo: tempos) {
			
			if(tempo.getHrFim().isBefore(horaAtual) ) {
				tempo.setStatus("FECHADO");
				this.controleTempoRepository.save(tempo);
			}
			
		}
				
	}
	
}
