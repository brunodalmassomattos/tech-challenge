package br.com.fiap.newparquimetro.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.newparquimetro.controller.exception.ControllerExceptionHandler;
import br.com.fiap.newparquimetro.controller.exception.ControllerNotFoundException;
import br.com.fiap.newparquimetro.domain.condutor.Condutor;
import br.com.fiap.newparquimetro.domain.condutor.Tempo;
import br.com.fiap.newparquimetro.domain.emissaorecibo.Tarifa;
import br.com.fiap.newparquimetro.domain.opcoesDePagamento.OpcoesDePagamento;
import br.com.fiap.newparquimetro.dto.controletempo.ControleTempoResponseDTO;
import br.com.fiap.newparquimetro.dto.enums.TipoTarifaEnum;
import br.com.fiap.newparquimetro.repositories.CondutorRepository;
import br.com.fiap.newparquimetro.repositories.ControleTempoRepository;
import br.com.fiap.newparquimetro.repositories.OpcoesDePagamentoRepository;
import br.com.fiap.newparquimetro.repositories.TarifaRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ControleTempoService {

    private final ControleTempoRepository controleTempoRepository;
    
    private final CondutorRepository condutorRepository;

    private final TarifaRepository tarifaRepository;
    
	private final OpcoesDePagamentoRepository opcoesDePagamentoRepository;
    
    public List<ControleTempoResponseDTO> buscaTempo(String idCondutor, String status) {
    	    
    	return ControleTempoResponseDTO.toDtoList(this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor,status)) ;
    	
    }
    
    public ControleTempoResponseDTO save(String idCondutor, Long tempoContratado,String tipo) {

    	this.condutorRepository.findById(idCondutor).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrado")); 	
    	
        List<Tempo> tempoExistente = this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor, "ABERTO");
    	
        if(!(tipo.equals("FIXO") || tipo.equals("VARIAVEL"))) {
        	throw new IllegalArgumentException("Tipo Inexistente");
        }
        
        if(!tempoExistente.isEmpty()) {
        	throw new IllegalArgumentException("Existe tempo em aberto para esse condutor");
        }
        
    	if(tempoContratado == null && tipo.equals("FIXO")) {
    		throw new IllegalArgumentException("Tempo vazio para tipo FIXO");
    	}
    	
    	Tempo tempo = new Tempo();
    	
        LocalTime horaAtual = LocalTime.now();
        LocalDate dataAtual = LocalDate.now();
        
        tempo.setData(Date.from(dataAtual.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        tempo.setHrInicio(horaAtual);
        tempo.setHrFim(horaAtual.plusMinutes(tipo.equals("FIXO") ? tempoContratado : 60));
        tempo.setIdCondutor(idCondutor);
        tempo.setStatus("ABERTO");
        tempo.setIdTarifa(tarifaRepository.findIdTipoTarifa(TipoTarifaEnum.getByDescricao(tipo)));
        
        return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));

    }
    
    public ControleTempoResponseDTO update(String idCondutor, Long tempoContratado) {
        
    	this.condutorRepository.findById(idCondutor).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrado"));
    	
    	Tempo tempo = this.controleTempoRepository.findTempoAtivoCondutorStatus(idCondutor,"ABERTO").get(0);
    	
    	if(this.tarifaRepository.findTipoById(tempo.getIdTarifa()).equals("VARIAVEL")) {
    		throw new IllegalArgumentException("Tempo em aberto variavel");
    	}
    	
    	tempo.setHrFim(tempo.getHrFim().plusMinutes(tempoContratado));

    	return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));
    	
    }
    
    public ControleTempoResponseDTO fechaTempo(String idTempo) {
    	
    	Tempo tempo = this.controleTempoRepository.findById(idTempo).orElseThrow(() -> new ControllerNotFoundException("Tempo não encontrado")); 	
    	
	    if(tempo.getStatus().equals("FECHADO")) {
    		throw new IllegalArgumentException("O tempo ja se encontra fechado");
	    }
	    
    	tempo.setStatus("FECHADO");
    	
    	LocalDate dataIni = LocalDate.now();
		LocalDate dataFim = dataIni.plusDays(1);
		
		this.controleTempoRepository.save(tempo);
		
		Tarifa tarifa = this.tarifaRepository.findById(tempo.getIdTarifa()).orElseThrow(() -> new ControllerNotFoundException("Tarifa não encontrada"));
	
		Condutor condutor = this.condutorRepository.findById(tempo.getIdCondutor()).orElseThrow(() -> new ControllerNotFoundException("Condutor não encontrada"));
		
		Duration duracao = Duration.between(tempo.getHrInicio(), tempo.getHrFim());
		
		long minutosTrabalhadas = duracao.toMinutes();
		
		BigDecimal valor = BigDecimal.valueOf(minutosTrabalhadas).multiply(BigDecimal.valueOf(tarifa.getValor()/60));
		
		this.opcoesDePagamentoRepository.save(OpcoesDePagamento.builder()
				.tipo(this.tarifaRepository.findTipoById(tempo.getIdTarifa()))
				.status("PENDENTE")
				.valor(valor)
				.idTempo(tempo.getId())
				.condutor(condutor)
				.build());
    	
    	return ControleTempoResponseDTO.toDto(this.controleTempoRepository.save(tempo));

    	
    }
    
	
}
