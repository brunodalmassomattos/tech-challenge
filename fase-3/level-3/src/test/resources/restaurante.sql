INSERT INTO tipos_restaurantes(id, descricao)
     VALUES ('23dd8526-8836-457f-ad5b-26e31d50820d', 'TIPO TESTE');

INSERT INTO enderecos(id, cep, logradouro, numero, bairro, cidade, estado)
     VALUES ('d87c2152-4591-4617-8688-18de21542273', '12345678', 'TESTE RUA', 'TESTE NUMERO',
             'TESTE BAIRRO', 'TESTE CIDADE', 'TESTE ESTADO',);

INSERT INTO restaurantes(id, nome, horarioFuncionamento, capacidade, status, endereco_id, tipo_restaurante_id)
     VALUES ('ce2f69e4-88c7-4134-91eb-302b0ce8edf5', 'TESTE NOME', 'TESTE HORARIO', '1', 'TRUE', 'd87c2152-4591-4617-8688-18de21542273', '23dd8526-8836-457f-ad5b-26e31d50820d');