package br.com.fiap.pedido.domain.models;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pedido")
@Builder
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	private UUID produto_id;
	
	private int quantidade;

	private String valor;
	
	private UUID usuario_id;
	
	private String forma_pagamento;
	
	private String nota_fiscal;
	
	@Temporal(TemporalType.DATE)
	private Date data_compra;
	
	private String status;
	
	private UUID codigo_entrega;
	
}
