package br.com.fiap.level3.domain.restaurante.core.domain.model.endereco;

public record EnderecoDTO(String id,
                          String cep,
                          String logradouro,
                          String numero,
                          String bairro,
                          String cidade,
                          String uf) {

    public static EnderecoDTO fromEndereco(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getId().toString(),
                endereco.getCep(),
                endereco.getLogradouro(),
                endereco.getNumero(),
                endereco.getBairro(),
                endereco.getCidade(),
                endereco.getEstado());
    }

}
