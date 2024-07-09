package br.com.fiap.newparquimetro.dto.condutor;

import jakarta.validation.constraints.NotBlank;

public record EnderecoDTO(

        @NotBlank(message = "O Lougradouro é obrigatorio!")
        String logradouro,
        @NotBlank(message = "O número é obrigatorio! Se não tiver número inserir S/N")
        String numero,
        String complemento,
        @NotBlank(message = "O Bairro é obrigatorio!")
        String bairro,
        @NotBlank(message = "O cidade é obrigatorio!")
        String cidade,
        @NotBlank(message = "O estado é obrigatorio!")
        String estado,
        @NotBlank(message = "O cep é obrigatorio!")
        String cep) {
}
