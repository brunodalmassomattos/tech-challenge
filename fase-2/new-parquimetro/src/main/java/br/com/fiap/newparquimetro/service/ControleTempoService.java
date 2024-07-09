package br.com.fiap.newparquimetro.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.dto.controletempo.ControleTempoResponseDTO;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ControleTempoService {

    private final ControleTempoRepository controleTempoRepository;
    
    private final CondutorRepository condutorRepository;
        
    public List<ControleTempoResponseDTO> buscaTempo(String idCondutor, String status) {
    	
    	return ControleTempoResponseDTO.toDtoList(this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor,status));
    	
    }
    
    public ControleTempoResponseDTO save(String idCondutor, Long tempoContratado) {

    	this.condutorRepository.findById(idCondutor).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrado"));
    	
    	Tempo tempo = new Tempo();
    	
        LocalTime horaAtual = LocalTime.now();
        LocalDate dataAtual = LocalDate.now();
        
        tempo.setData(Date.from(dataAtual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        tempo.setHrInicio(horaAtual);
        tempo.setHrFim(horaAtual.plusMinutes(tempoContratado));
        tempo.setIdCondutor(idCondutor);
        tempo.setStatus("ABERTO");
        
        return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));

    }
    
    public ControleTempoResponseDTO update(String idCondutor, Long tempoContratado) {
        
    	this.condutorRepository.findById(idCondutor).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrado"));
    	
    	Tempo tempo = this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor,"ABERTO").get(0);
    	
    	tempo.setHrFim(tempo.getHrFim().plusMinutes(tempoContratado));

    	return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));
    	
    }
    
	
}
